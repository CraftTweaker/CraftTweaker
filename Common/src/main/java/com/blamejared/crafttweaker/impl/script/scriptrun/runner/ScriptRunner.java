package com.blamejared.crafttweaker.impl.script.scriptrun.runner;

import com.blamejared.crafttweaker.CraftTweakerCommon;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.ICraftTweakerRegistry;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunInfo;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunModuleConfigurator;
import com.blamejared.crafttweaker.api.zencode.scriptrun.ScriptRunConfiguration;
import com.blamejared.crafttweaker.impl.script.scriptrun.natives.CtJavaNativeConverterBuilder;
import com.google.common.base.Suppliers;
import com.mojang.datafixers.util.Pair;
import net.minecraft.Util;
import org.openzen.zencode.java.ScriptingEngine;
import org.openzen.zencode.java.logger.ScriptingEngineLogger;
import org.openzen.zencode.java.module.JavaNativeModule;
import org.openzen.zencode.java.module.converters.JavaNativeConverterBuilder;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zencode.shared.SourceFile;
import org.openzen.zenscript.codemodel.FunctionParameter;
import org.openzen.zenscript.codemodel.SemanticModule;
import org.openzen.zenscript.lexer.ParseException;
import org.openzen.zenscript.parser.BracketExpressionParser;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

sealed public abstract class ScriptRunner permits ExecutingScriptRunner, FormattingScriptRunner, SyntaxCheckScriptRunner {
    
    @FunctionalInterface
    private interface ScriptRunCreator {
        
        ScriptRunner of(final IScriptRunInfo info, final List<SourceFile> sources, final ScriptingEngineLogger logger);
        
    }
    
    private static final Supplier<Map<ScriptRunConfiguration.RunKind, ScriptRunCreator>> CREATORS =
            Suppliers.memoize(() -> Map.of(
                    ScriptRunConfiguration.RunKind.SYNTAX_CHECK, SyntaxCheckScriptRunner::new,
                    ScriptRunConfiguration.RunKind.FORMAT, FormattingScriptRunner::new,
                    ScriptRunConfiguration.RunKind.EXECUTE, ExecutingScriptRunner::new
            ));
    
    private final IScriptRunInfo runInfo;
    private final List<SourceFile> sources;
    private final ScriptingEngine scriptingEngine;
    
    protected ScriptRunner(final IScriptRunInfo runInfo, final List<SourceFile> sources, final ScriptingEngineLogger logger) {
        
        this.runInfo = runInfo;
        this.sources = List.copyOf(sources);
        this.scriptingEngine = Util.make(
                new ScriptingEngine(logger, ScriptRunner.class::getResourceAsStream),
                it -> it.debug = runInfo.dumpClasses()
        );
    }
    
    public static ScriptRunner of(final IScriptRunInfo info, final List<SourceFile> sources, final ScriptingEngineLogger logger) {
        
        final ScriptRunConfiguration.RunKind kind = info.configuration().runKind();
        final ScriptRunCreator creator = Objects.requireNonNull(CREATORS.get().get(kind), "Unknown kind specified");
        return creator.of(info, sources, logger);
    }
    
    public final void run() throws Exception {
        
        final BracketExpressionParser parser = this.initializeEngine();
        this.runScripts(parser);
    }
    
    private BracketExpressionParser initializeEngine() throws CompileException {
        
        final CtJavaNativeConverterBuilder converterBuilder = new CtJavaNativeConverterBuilder(this.runInfo);
        final ICraftTweakerRegistry registry = CraftTweakerAPI.getRegistry();
        final IgnorePrefixCasingBracketParser parser = this.createParser();
        final Collection<JavaNativeModule> modules = this.gatherModules(converterBuilder, registry, parser);
        
        converterBuilder.reinitializeLazyHeaderValues();
        
        final AtomicReference<CompileException> thrown = new AtomicReference<>(null);
        modules.forEach(it -> this.registerModule(it, thrown));
        if(thrown.get() != null) {
            throw thrown.get();
        }
        
        return parser;
    }
    
    private void runScripts(final BracketExpressionParser parser) throws ParseException {
        
        final SourceFile[] sources = this.sources.toArray(SourceFile[]::new);
        final SemanticModule module = this.engine()
                .createScriptedModule("scripts", sources, parser, FunctionParameter.NONE);
        
        if(!module.isValid()) {
            Stream.of(CraftTweakerAPI.LOGGER, CraftTweakerCommon.LOG).forEach(it -> it.error("Scripts are invalid!"));
            return;
        }
        
        this.executeRunAction(module);
    }
    
    protected abstract void executeRunAction(final SemanticModule module);
    
    private Collection<JavaNativeModule> gatherModules(final JavaNativeConverterBuilder builder, final ICraftTweakerRegistry registry, final IgnorePrefixCasingBracketParser parser) throws CompileException {
        
        final IScriptLoader loader = this.runInfo().loader();
        final IScriptRunModuleConfigurator configurator = registry.getConfiguratorFor(loader);
        return configurator.populateModules(builder, registry, this.runInfo()
                .configuration(), (name, rootPackage, builder1, dependencies) -> createNativeModule(name, rootPackage, builder1, registry, parser, dependencies));
    }
    
    private void registerModule(final JavaNativeModule module, final AtomicReference<CompileException> ref) {
        
        try {
            this.engine().registerNativeProvided(module);
        } catch(final CompileException e) {
            if(!ref.compareAndSet(null, e)) {
                ref.get().addSuppressed(e);
            }
        }
    }
    
    private JavaNativeModule createNativeModule(
            final String name,
            final String rootPackage,
            final JavaNativeConverterBuilder builder,
            final ICraftTweakerRegistry registry,
            final IgnorePrefixCasingBracketParser parser,
            final JavaNativeModule... dependencies
    ) {
        
        JavaNativeModule module = this.engine().createNativeModule(name, rootPackage, dependencies, builder);
        this.getBracketsFor(module, registry).forEach(parser::register);
        module.registerBEP(parser);
        return module;
    }
    
    private IgnorePrefixCasingBracketParser createParser() {
        
        return new IgnorePrefixCasingBracketParser();
    }
    
    private Map<String, BracketExpressionParser> getBracketsFor(final JavaNativeModule module, final ICraftTweakerRegistry registry) {
        // TODO This needs to use the root package to find brackets only in that package and register them for the module
        return registry.getBracketHandlers(this.runInfo().loader(), null, this.engine(), module)
                .stream()
                .collect(Collectors.toMap(
                        Pair::getFirst,
                        Pair::getSecond, (a, b) -> {
                            throw new IllegalStateException("Found two BEPs with the same name: " + a + " and " + b);
                        }));
    }
    
    protected IScriptRunInfo runInfo() {
        
        return this.runInfo;
    }
    
    protected List<SourceFile> sources() {
        
        return this.sources;
    }
    
    protected ScriptingEngine engine() {
        
        return this.scriptingEngine;
    }
    
}
