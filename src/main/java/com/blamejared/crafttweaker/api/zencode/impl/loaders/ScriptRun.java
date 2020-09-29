package com.blamejared.crafttweaker.api.zencode.impl.loaders;

import com.blamejared.crafttweaker.*;
import com.blamejared.crafttweaker.api.*;
import com.blamejared.crafttweaker.api.managers.*;
import com.blamejared.crafttweaker.api.zencode.brackets.*;
import com.blamejared.crafttweaker.impl.brackets.*;
import com.blamejared.crafttweaker.impl.logger.*;
import org.openzen.zencode.java.*;
import org.openzen.zencode.shared.*;
import org.openzen.zenscript.codemodel.*;
import org.openzen.zenscript.formatter.*;

import java.io.*;
import java.util.*;

public class ScriptRun {
    
    private final ScriptLoadingOptions scriptLoadingOptions;
    private final SourceFile[] sourceFiles;
    private final ScriptingEngine scriptingEngine;
    
    public ScriptRun(ScriptLoadingOptions scriptLoadingOptions, SourceFile[] sourceFiles) {
        this.scriptLoadingOptions = scriptLoadingOptions;
        this.sourceFiles = sourceFiles;
    
        //Init Engine
        this.scriptingEngine = new ScriptingEngine(CraftTweakerAPI.logger);
        this.scriptingEngine.debug = CraftTweakerAPI.DEBUG_MODE;
    }
    
    public LoaderActions getLoaderActions() {
        if(scriptLoadingOptions.isWildcardLoader()) {
            throw new IllegalStateException("Cannot get LoaderActions for wildcard loader");
        }
        
        return LoaderActions.getActionForLoader(scriptLoadingOptions.getLoaderName());
    }
    
    public boolean isFirstRun() {
        return scriptLoadingOptions.isFirstRun() || getLoaderActions().isFirstRun();
    }
    
    public ScriptingEngine getEngine() {
        return scriptingEngine;
    }
    
    public void reload() {
        if(scriptLoadingOptions.isExecute()) {
            getLoaderActions().reload();
        }
        
        //Unless another mod messes up, that should always be a GroupLogger
        if(CraftTweakerAPI.logger instanceof GroupLogger) {
            ((GroupLogger) CraftTweakerAPI.logger).getPreviousMessages().clear();
        } else {
            final String message = "Internal Error: Expected Logger to be a GroupLogger, but found '%s'. This is most likely an issue with a CrT addon!";
            final String loggerClassName = CraftTweakerAPI.logger.getClass().getCanonicalName();
            CraftTweakerAPI.logError(message, loggerClassName);
        }
    }
    
    /**
     * Starts the scriptRun
     *
     * @throws Exception Any exception that occurs while running the script file.
     */
    public void run() throws Exception {
        if(!scriptLoadingOptions.isExecute()) {
            CraftTweakerAPI.logInfo("This is only a syntax check. Script changes will not be applied.");
        }
        
        if(isFirstRun()) {
            CraftTweakerAPI.logDebug("This is a first run. All IActions will be applied.");
        }
        
        //Register crafttweaker module first to assign deps
        JavaNativeModule crafttweakerModule = scriptingEngine.createNativeModule(CraftTweaker.MODID, "crafttweaker");
        List<JavaNativeModule> modules = new LinkedList<>();
        
        IgnorePrefixCasingBracketParser bep = new IgnorePrefixCasingBracketParser();
        final List<Class<? extends IRecipeManager>> recipeManagers = CraftTweakerRegistry.getRecipeManagers();
        bep.register("recipetype", new RecipeTypeBracketHandler(recipeManagers));
        for(ValidatedEscapableBracketParser bracketResolver : CraftTweakerRegistry.getBracketResolvers("crafttweaker", scriptingEngine, crafttweakerModule)) {
            bep.register(bracketResolver.getName(), bracketResolver);
        }
        crafttweakerModule.registerBEP(bep);
        
        CraftTweakerRegistry.getClassesInPackage("crafttweaker")
                .forEach(crafttweakerModule::addClass);
        CraftTweakerRegistry.getZenGlobals().forEach(crafttweakerModule::addGlobals);
        modules.add(crafttweakerModule);
        
        scriptingEngine.registerNativeProvided(crafttweakerModule);
        for(String key : CraftTweakerRegistry.getRootPackages()) {
            //module already registered
            if(key.equals("crafttweaker")) {
                continue;
            }
            JavaNativeModule module = scriptingEngine.createNativeModule(key, key, crafttweakerModule);
            module.registerBEP(bep);
            for(ValidatedEscapableBracketParser bracketResolver : CraftTweakerRegistry.getBracketResolvers(key, scriptingEngine, module)) {
                bep.register(bracketResolver.getName(), bracketResolver);
            }
            CraftTweakerRegistry.getClassesInPackage(key).forEach(module::addClass);
            scriptingEngine.registerNativeProvided(module);
            modules.add(module);
        }
        
        // For expansions on ZenScript types (I.E. any[any], string, int) and just anything else that fails
        JavaNativeModule expansions = scriptingEngine.createNativeModule("expansions", "", modules.toArray(new JavaNativeModule[0]));
        CraftTweakerRegistry.getExpansions()
                .values()
                .stream()
                .flatMap(Collection::stream)
                .forEach(expansions::addClass);
        scriptingEngine.registerNativeProvided(expansions);
        
        
        SemanticModule scripts = scriptingEngine.createScriptedModule("scripts", sourceFiles, bep, FunctionParameter.NONE);
        
        if(!scripts.isValid()) {
            CraftTweakerAPI.logger.error("Scripts are invalid!");
            CraftTweaker.LOG.info("Scripts are invalid!");
            return;
        }
        
        //  toggle this to format scripts, ideally this should be a command
        if(scriptLoadingOptions.isFormat()) {
            writeFormattedFiles(scripts);
        }
        
        if(scriptLoadingOptions.isExecute()) {
            //Now that we execute, we increment the runCount and therefore it's no longer a first run
            final LoaderActions loaderActions = getLoaderActions();
            loaderActions.incrementRunCount();
            CraftTweakerAPI.logDebug("This is loader '%s' run #%s", scriptLoadingOptions.getLoaderName(), loaderActions.getRunCount());
            
            scriptingEngine.registerCompiled(scripts);
            scriptingEngine.run(Collections.emptyMap(), CraftTweaker.class.getClassLoader());
            
        } else if(CraftTweakerAPI.DEBUG_MODE) {
            scriptingEngine.createRunUnit().dump(new File("classes"));
        }
    }
    
    private void writeFormattedFiles(SemanticModule scripts) throws IOException {
        List<HighLevelDefinition> all = scripts.definitions.getAll();
        ScriptFormattingSettings.Builder builder = new ScriptFormattingSettings.Builder();
        FileFormatter formatter = new FileFormatter(builder.build());
        List<ScriptBlock> blocks = scripts.scripts;
        for(ScriptBlock block : blocks) {
            String format = formatter.format(scripts.rootPackage, block, all);
            File parent = new File("scriptsFormatted");
            File file = new File(parent, block.file.getFilename());
            
            if(!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
                CraftTweakerAPI.logError("Could not find or create folder %s, aborting formatting task!", file
                        .getParent());
            }
            file.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(format);
            writer.close();
        }
    }
}
