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
import org.openzen.zencode.java.ScriptingEngine;
import org.openzen.zencode.java.logger.ScriptingEngineLogger;
import org.openzen.zencode.java.module.JavaNativeModule;
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
import java.util.function.Consumer;
import java.util.function.Supplier;

sealed abstract class ScriptRunner implements IScriptRunner permits ExecutingScriptRunner, FormattingScriptRunner, SyntaxCheckScriptRunner, GameTestScriptRunner {
    
    @FunctionalInterface
    private interface ScriptRunCreator {
        
        ScriptRunner of(final IScriptRunInfo info, final List<SourceFile> sources, final ScriptingEngineLogger logger);
        
    }
    
    private static final Supplier<Map<ScriptRunConfiguration.RunKind, ScriptRunCreator>> CREATORS =
            Suppliers.memoize(() -> Map.of(
                    ScriptRunConfiguration.RunKind.SYNTAX_CHECK, SyntaxCheckScriptRunner::new,
                    ScriptRunConfiguration.RunKind.FORMAT, FormattingScriptRunner::new,
                    ScriptRunConfiguration.RunKind.EXECUTE, ExecutingScriptRunner::new,
                    ScriptRunConfiguration.RunKind.GAME_TEST, GameTestScriptRunner::new
            ));
    
    private final IScriptRunInfo runInfo;
    private final List<SourceFile> sources;
    private final ScriptingEngine scriptingEngine;
    
    protected ScriptRunner(final IScriptRunInfo runInfo, final List<SourceFile> sources, final ScriptingEngineLogger logger) {
        
        this.runInfo = runInfo;
        this.sources = List.copyOf(sources);
        this.scriptingEngine = new ScriptingEngine(logger, ScriptRunner.class::getResourceAsStream);
        this.scriptingEngine.debug = runInfo.dumpClasses();
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
        
        final ICraftTweakerRegistry registry = CraftTweakerAPI.getRegistry();
        final CtJavaNativeConverterBuilder converterBuilder = new CtJavaNativeConverterBuilder(this.runInfo, registry.getZenClassRegistry());
        final BracketExpressionParser parser = this.createParser(registry);
        final Collection<DecoratedJavaNativeModule> modules = this.populateModules(converterBuilder, registry, parser);
        this.scriptingEngine.logger.info("Successfully initialized modules " + modules);
        return parser;
    }
    
    protected void runScripts(final BracketExpressionParser parser) throws ParseException {
        
        final SourceFile[] sources = this.sources.toArray(SourceFile[]::new);
        final SemanticModule module = this.engine()
                .createScriptedModule("scripts", sources, parser, FunctionParameter.NONE);
        
        if(!module.isValid()) {
            CraftTweakerCommon.logger().error("Scripts are invalid!");
            return;
        }
        
        this.executeRunAction(module);
    }
    
    protected abstract void executeRunAction(final SemanticModule module);
    
    private Collection<DecoratedJavaNativeModule> populateModules(
            final CtJavaNativeConverterBuilder builder,
            final ICraftTweakerRegistry registry,
            final BracketExpressionParser parser
    ) throws CompileException {
        
        return this.gatherModules(builder, registry, parser)
                .stream()
                .map(DecoratedJavaNativeModule::new)
                .toList();
    }
    
    private Collection<JavaNativeModule> gatherModules(
            final CtJavaNativeConverterBuilder builder,
            final ICraftTweakerRegistry registry,
            final BracketExpressionParser parser
    ) throws CompileException {
        
        final IScriptLoader loader = this.runInfo().loader();
        final IScriptRunModuleConfigurator configurator = registry.getConfiguratorFor(loader);
        final ScriptRunConfiguration configuration = this.runInfo().configuration();
        return configurator.populateModules(
                registry,
                configuration,
                (name, root, dependencies, config) -> this.createNativeModule(name, root, builder, parser, dependencies, config)
        );
    }
    
    private JavaNativeModule createNativeModule(
            final String name,
            final String rootPackage,
            final CtJavaNativeConverterBuilder builder,
            final BracketExpressionParser parser,
            final List<JavaNativeModule> dependenciesList,
            final Consumer<JavaNativeModule> configurator
    ) throws CompileException {
        
        final JavaNativeModule[] dependencies = dependenciesList.toArray(JavaNativeModule[]::new);
        final JavaNativeModule module = this.engine().createNativeModule(name, rootPackage, dependencies, builder);
        module.registerBEP(parser);
        configurator.accept(module);
        this.engine().registerNativeProvided(module);
        builder.reinitializeLazyHeaderValues();
        return module;
    }
    
    private BracketExpressionParser createParser(final ICraftTweakerRegistry registry) {
        
        return new IgnorePrefixCasingBracketParser(this.getBracketsFor(registry));
    }
    
    private Map<String, BracketExpressionParser> getBracketsFor(final ICraftTweakerRegistry registry) {
        
        // TODO("Does this need the root package data?")
        return registry.getBracketHandlers(this.runInfo().loader(), null);
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
