package com.blamejared.crafttweaker.api.zencode.impl.loaders;

import com.blamejared.crafttweaker.*;
import com.blamejared.crafttweaker.api.*;
import com.blamejared.crafttweaker.api.managers.*;
import com.blamejared.crafttweaker.api.zencode.brackets.*;
import com.blamejared.crafttweaker.impl.brackets.*;
import com.blamejared.crafttweaker.impl.brackets.tags.*;
import com.blamejared.crafttweaker.impl.logger.*;
import com.blamejared.crafttweaker.impl.tag.registry.*;
import net.minecraftforge.common.*;
import org.openzen.zencode.java.*;
import org.openzen.zencode.shared.*;
import org.openzen.zenscript.codemodel.*;
import org.openzen.zenscript.formatter.*;
import org.openzen.zenscript.lexer.*;

import java.io.*;
import java.util.*;

public class ScriptRun {
    
    private final ScriptLoadingOptions scriptLoadingOptions;
    private final SourceFile[] sourceFiles;
    private final ScriptingEngine scriptingEngine;
    private IgnorePrefixCasingBracketParser bep;
    
    public ScriptRun(ScriptLoadingOptions scriptLoadingOptions, SourceFile[] sourceFiles) {
        this.scriptLoadingOptions = scriptLoadingOptions;
        this.sourceFiles = sourceFiles;
        
        //Init Engine
        this.scriptingEngine = new ScriptingEngine(CraftTweakerAPI.logger);
        this.scriptingEngine.debug = CraftTweakerAPI.DEBUG_MODE;
    }
    
    public LoaderActions getLoaderActions() {
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
        
        initializeBep();
        registerModules();
        readAndExecuteScripts();
        
    }
    
    private void initializeBep() {
        this.bep = new IgnorePrefixCasingBracketParser();
        MinecraftForge.EVENT_BUS.post(new CTRegisterBEPEvent(bep));
    }
    
    private void readAndExecuteScripts() throws ParseException, IOException {
        SemanticModule scripts = scriptingEngine.createScriptedModule("scripts", sourceFiles, bep, FunctionParameter.NONE);
        
        if(!scripts.isValid()) {
            CraftTweakerAPI.logger.error("Scripts are invalid!");
            CraftTweaker.LOG.info("Scripts are invalid!");
            return;
        }
        
        //  toggle this to format scripts. Set by /ct format
        if(scriptLoadingOptions.isFormat()) {
            writeFormattedFiles(scripts);
        }
        
        if(scriptLoadingOptions.isExecute()) {
            //Now that we execute, we increment the runCount and therefore it's no longer a first run
            final LoaderActions loaderActions = getLoaderActions();
            loaderActions.incrementRunCount();
            CraftTweakerAPI.logDebug("This is loader '%s' run #%s", scriptLoadingOptions.getLoaderName(), loaderActions
                    .getRunCount());
            
            scriptingEngine.registerCompiled(scripts);
            scriptingEngine.run(Collections.emptyMap(), CraftTweaker.class.getClassLoader());
            
        } else if(CraftTweakerAPI.DEBUG_MODE) {
            scriptingEngine.createRunUnit().dump(new File("classes"));
        }
    }
    
    private void registerModules() throws CompileException {
        final List<JavaNativeModule> modules = new LinkedList<>();
        
        //Register crafttweaker module first to assign deps
        final JavaNativeModule crafttweakerModule = createModule(bep, CraftTweaker.MODID, CraftTweaker.MODID);
        for(Class<?> aClass : CraftTweakerRegistry.getZenGlobals()) {
            crafttweakerModule.addGlobals(aClass);
        }
        scriptingEngine.registerNativeProvided(crafttweakerModule);
        modules.add(crafttweakerModule);
        
        final HashSet<String> rootPackages = new HashSet<>(CraftTweakerRegistry.getRootPackages());
        rootPackages.remove(CraftTweaker.MODID);
        for(String rootPackage : rootPackages) {
            final JavaNativeModule module = createModule(bep, rootPackage, rootPackage, crafttweakerModule);
            scriptingEngine.registerNativeProvided(module);
            modules.add(module);
        }
        
        
        final JavaNativeModule expModule = createModule(bep, "expansions", "", modules.toArray(new JavaNativeModule[0]));
        for(List<Class<?>> expansionList : CraftTweakerRegistry.getExpansions().values()) {
            for(Class<?> expansionClass : expansionList) {
                expModule.addClass(expansionClass);
            }
        }
        scriptingEngine.registerNativeProvided(expModule);
    }
    
    private JavaNativeModule createModule(IgnorePrefixCasingBracketParser bep, String moduleName, String basePackage, JavaNativeModule... dependencies) {
        JavaNativeModule module = scriptingEngine.createNativeModule(moduleName, basePackage, dependencies);
        for(ValidatedEscapableBracketParser bracketResolver : CraftTweakerRegistry.getBracketResolvers(moduleName, scriptingEngine, module)) {
            bep.register(bracketResolver.getName(), bracketResolver);
        }
        module.registerBEP(bep);
        
        for(Class<?> aClass : CraftTweakerRegistry.getClassesInPackage(moduleName)) {
            module.addClass(aClass);
        }
        return module;
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
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(format);
            writer.close();
        }
    }
}
