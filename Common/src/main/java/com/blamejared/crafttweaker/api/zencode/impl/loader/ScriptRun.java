package com.blamejared.crafttweaker.api.zencode.impl.loader;

import com.blamejared.crafttweaker.CraftTweakerCommon;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.ICraftTweakerRegistry;
import com.blamejared.crafttweaker.api.ScriptLoadingOptions;
import com.blamejared.crafttweaker.api.bracket.custom.EnumConstantBracketHandler;
import com.blamejared.crafttweaker.api.bracket.custom.RecipeTypeBracketHandler;
import com.blamejared.crafttweaker.api.bracket.custom.TagBracketHandler;
import com.blamejared.crafttweaker.api.bracket.custom.TagManagerBracketHandler;
import com.blamejared.crafttweaker.api.logger.CraftTweakerLogger;
import com.blamejared.crafttweaker.api.logger.ForwardingSELogger;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.tag.registry.CrTTagRegistryData;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.blamejared.crafttweaker.api.zencode.bracket.IgnorePrefixCasingBracketParser;
import com.blamejared.crafttweaker.api.zencode.impl.native_type.CrTJavaNativeConverterBuilder;
import com.blamejared.crafttweaker.platform.Services;
import org.openzen.zencode.java.ScriptingEngine;
import org.openzen.zencode.java.module.JavaNativeModule;
import org.openzen.zencode.java.module.converters.JavaNativeConverterBuilder;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zencode.shared.SourceFile;
import org.openzen.zenscript.codemodel.FunctionParameter;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.ScriptBlock;
import org.openzen.zenscript.codemodel.SemanticModule;
import org.openzen.zenscript.formatter.FileFormatter;
import org.openzen.zenscript.formatter.ScriptFormattingSettings;
import org.openzen.zenscript.lexer.ParseException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class ScriptRun {
    
    private final ScriptLoadingOptions scriptLoadingOptions;
    private final SourceFile[] sourceFiles;
    private final ScriptingEngine scriptingEngine;
    private IgnorePrefixCasingBracketParser bep;
    
    public ScriptRun(ScriptLoadingOptions scriptLoadingOptions, SourceFile[] sourceFiles) {
        
        this.scriptLoadingOptions = scriptLoadingOptions;
        this.sourceFiles = sourceFiles;
        
        //Init Engine
        this.scriptingEngine = new ScriptingEngine(ForwardingSELogger.INSTANCE, CraftTweakerCommon.class::getResourceAsStream);
        this.scriptingEngine.debug = CraftTweakerAPI.DEBUG_MODE;
    }
    
    public LoaderActions getLoaderActions() {
        
        return LoaderActions.getActionForLoader(scriptLoadingOptions.getLoaderName(), scriptLoadingOptions.getSource());
    }
    
    public ScriptLoadingOptions.ScriptLoadSource getScriptLoadSource() {
        
        return scriptLoadingOptions.getSource();
    }
    
    public boolean isFirstRun() {
        
        return getLoaderActions().isFirstRun();
    }
    
    public ScriptingEngine getEngine() {
        
        return scriptingEngine;
    }
    
    public void reload() {
        
        if(scriptLoadingOptions.isExecute()) {
            getLoaderActions().reload();
        }
        
        CraftTweakerLogger.clearPreviousMessages();
    }
    
    /**
     * Starts the scriptRun
     *
     * @throws Exception Any exception that occurs while running the script file.
     */
    public void run() throws Exception {
        
        if(!scriptLoadingOptions.isExecute()) {
            CraftTweakerAPI.LOGGER.info("This is only a syntax check. Script changes will not be applied.");
        }
        
        if(isFirstRun()) {
            CraftTweakerAPI.LOGGER.debug("This is a first run. All IActions will be applied.");
        }
        
        initializeBep();
        registerModules();
        readAndExecuteScripts();
        
    }
    
    private void initializeBep() {
        
        this.bep = new IgnorePrefixCasingBracketParser();
        Services.EVENT.fireRegisterBEPEvent(bep);
        
        final ICraftTweakerRegistry registry = CraftTweakerAPI.getRegistry();
        final IScriptLoader loader = registry.findLoader(this.scriptLoadingOptions.getLoaderName());
        final List<Class<? extends IRecipeManager>> recipeManagers = registry.getZenClassRegistry()
                .getImplementationsOf(loader, IRecipeManager.class);
        bep.register("recipetype", new RecipeTypeBracketHandler(recipeManagers));
        bep.register("constant", new EnumConstantBracketHandler());
        
        
        final TagManagerBracketHandler tagManagerBEP = new TagManagerBracketHandler(CrTTagRegistryData.INSTANCE);
        bep.register("tagManager", tagManagerBEP);
        bep.register("tag", new TagBracketHandler(tagManagerBEP));
    }
    
    private void readAndExecuteScripts() throws ParseException {
        
        SemanticModule scripts = scriptingEngine.createScriptedModule("scripts", sourceFiles, bep, FunctionParameter.NONE);
        
        if(!scripts.isValid()) {
            CraftTweakerAPI.LOGGER.error("Scripts are invalid!");
            CraftTweakerCommon.LOG.info("Scripts are invalid!");
            return;
        }
        
        //  toggle this to format scripts. Set by /ct format
        if(scriptLoadingOptions.isFormat()) {
            writeFormattedFiles(scripts);
        }
        
        if(scriptLoadingOptions.isExecute()) {
            //Now that we execute, we increment the runCount and therefore it's no longer a first run
            final LoaderActions loaderActions = getLoaderActions();
            CraftTweakerAPI.LOGGER.debug("This is loader '{}' run #{}", scriptLoadingOptions.getLoaderName(), loaderActions
                    .getRunCount() + 1);
            
            scriptingEngine.registerCompiled(scripts);
            scriptingEngine.run(Collections.emptyMap(), CraftTweakerCommon.class.getClassLoader());
            loaderActions.incrementRunCount();
            
        } else if(CraftTweakerAPI.DEBUG_MODE) {
            scriptingEngine.createRunUnit().dump(new File("classes"));
        }
    }
    
    private void registerModules() throws CompileException {
        
        final List<JavaNativeModule> modules = new LinkedList<>();
        final CrTJavaNativeConverterBuilder nativeConverterBuilder = new CrTJavaNativeConverterBuilder();
        
        //Register crafttweaker module first to assign deps
        final JavaNativeModule crafttweakerModule = createModule(bep, CraftTweakerConstants.MOD_ID, CraftTweakerConstants.MOD_ID, nativeConverterBuilder);
        
        scriptingEngine.registerNativeProvided(crafttweakerModule);
        modules.add(crafttweakerModule);
        
        final ICraftTweakerRegistry registry = CraftTweakerAPI.getRegistry();
        final IScriptLoader loader = registry.findLoader(this.scriptLoadingOptions.getLoaderName());
        
        final HashSet<String> rootPackages = new HashSet<>(registry.getZenClassRegistry().getRootPackages(loader));
        rootPackages.remove(CraftTweakerConstants.MOD_ID);
        for(String rootPackage : rootPackages) {
            final JavaNativeModule module = createModule(bep, rootPackage, rootPackage, nativeConverterBuilder, crafttweakerModule);
            scriptingEngine.registerNativeProvided(module);
            modules.add(module);
        }
        
        
        final JavaNativeModule expModule = createModule(bep, "expansions", "", nativeConverterBuilder, modules.toArray(new JavaNativeModule[0]));
        for(Class<?> expansionClass : registry.getZenClassRegistry().getClassData(loader).expansions().values()) {
            expModule.addClass(expansionClass);
        }
        scriptingEngine.registerNativeProvided(expModule);
        
        nativeConverterBuilder.headerConverter.reinitializeAllLazyValues();
    }
    
    private JavaNativeModule createModule(IgnorePrefixCasingBracketParser bep, String moduleName, String basePackage, JavaNativeConverterBuilder nativeConverterBuilder, JavaNativeModule... dependencies) {
        
        JavaNativeModule module = scriptingEngine.createNativeModule(moduleName, basePackage, dependencies, nativeConverterBuilder);
        
        final ICraftTweakerRegistry registry = CraftTweakerAPI.getRegistry();
        final IScriptLoader loader = registry.findLoader(this.scriptLoadingOptions.getLoaderName());
        
        registry.getBracketHandlers(loader, basePackage, this.scriptingEngine, module)
                .forEach(it -> bep.register(it.getFirst(), it.getSecond()));
        module.registerBEP(bep);
        
        for(Class<?> aClass : registry.getZenClassRegistry().getGlobalsInPackage(loader, moduleName)) {
            module.addGlobals(aClass);
        }
        for(Class<?> aClass : registry.getZenClassRegistry().getClassesInPackage(loader, moduleName)) {
            module.addClass(aClass);
        }
        
        return module;
    }
    
    private void writeFormattedFiles(SemanticModule scripts) {
        
        List<HighLevelDefinition> all = scripts.definitions.getAll();
        ScriptFormattingSettings.Builder builder = new ScriptFormattingSettings.Builder();
        FileFormatter formatter = new FileFormatter(builder.build());
        List<ScriptBlock> blocks = scripts.scripts;
        for(ScriptBlock block : blocks) {
            String format = formatter.format(scripts.rootPackage, block, all);
            File parent = new File("scriptsFormatted");
            File file = new File(parent, block.file.getFilename());
            
            if(!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
                CraftTweakerAPI.LOGGER.error("Could not find or create folder {}, aborting formatting task!", file
                        .getParent());
            }
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(format);
            } catch(IOException e) {
                CraftTweakerAPI.LOGGER.error("Could not write formatted files", e);
            }
        }
    }
    
}
