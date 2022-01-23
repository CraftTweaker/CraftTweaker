package com.blamejared.crafttweaker.api.zencode.impl.loader;

import com.blamejared.crafttweaker.CraftTweakerCommon;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.ScriptLoadingOptions;
import com.blamejared.crafttweaker.api.logger.CraftTweakerLogger;
import com.blamejared.crafttweaker.api.logger.ForwardingSELogger;
import com.blamejared.crafttweaker.api.zencode.bracket.IgnorePrefixCasingBracketParser;
import com.blamejared.crafttweaker.platform.Services;
import org.openzen.zencode.java.ScriptingEngine;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zencode.shared.SourceFile;
import org.openzen.zenscript.codemodel.FunctionParameter;
import org.openzen.zenscript.codemodel.HighLevelDefinition;
import org.openzen.zenscript.codemodel.SemanticModule;
import org.openzen.zenscript.formatter.FileFormatter;
import org.openzen.zenscript.formatter.ScriptFormattingSettings;
import org.openzen.zenscript.lexer.ParseException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
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
        
        if(!this.scriptLoadingOptions.isExecute()) {
            CraftTweakerAPI.LOGGER.info("This is only a syntax check. Script changes will not be applied.");
        }
        
        if(isFirstRun()) {
            CraftTweakerAPI.LOGGER.debug("This is a first run. All IActions will be applied.");
        }
        
        initializeBep();
        configureRun();
        readAndExecuteScripts();
    }
    
    private void initializeBep() {
        
        this.bep = new IgnorePrefixCasingBracketParser();
        Services.EVENT.fireCustomRegisterBepEvent(this.scriptLoadingOptions.getLoaderName(), this.bep::register);
    }
    
    private void configureRun() throws CompileException {
        
        this.scriptLoadingOptions.configureRun(this.bep, this.scriptingEngine);
    }
    
    private void readAndExecuteScripts() throws ParseException {
        
        final SemanticModule scripts = this.scriptingEngine.createScriptedModule("scripts", this.sourceFiles, this.bep, FunctionParameter.NONE);
        
        if(!scripts.isValid()) {
            
            CraftTweakerAPI.LOGGER.error("Scripts are invalid!");
            CraftTweakerCommon.LOG.info("Scripts are invalid!");
            return;
        }
        
        //  toggle this to format scripts. Set by /ct format
        if(this.scriptLoadingOptions.isFormat()) {
            
            writeFormattedFiles(scripts);
        }
        
        if(this.scriptLoadingOptions.isExecute()) {
            
            //Now that we execute, we increment the runCount and therefore it's no longer a first run
            final LoaderActions loaderActions = getLoaderActions();
            CraftTweakerAPI.LOGGER.debug("This is loader '{}' run #{}", scriptLoadingOptions.getLoaderName(), loaderActions
                    .getRunCount() + 1);
            
            this.scriptingEngine.registerCompiled(scripts);
            this.scriptingEngine.run(Collections.emptyMap(), CraftTweakerCommon.class.getClassLoader());
            loaderActions.incrementRunCount();
            
        } else if(CraftTweakerAPI.DEBUG_MODE) {
            this.scriptingEngine.createRunUnit().dump(Paths.get("classes").toFile());
        }
    }
    
    private void writeFormattedFiles(final SemanticModule scripts) {
        
        final List<HighLevelDefinition> all = scripts.definitions.getAll();
        final ScriptFormattingSettings.Builder builder = new ScriptFormattingSettings.Builder();
        final FileFormatter formatter = new FileFormatter(builder.build());
        final Path root = Paths.get("scriptsFormatted");
        
        scripts.scripts.forEach(block -> {
            String format = formatter.format(scripts.rootPackage, block, all);
            final Path file = root.resolve(block.file.getFilename());
            
            try {
                
                Files.createDirectories(file.getParent());
            } catch(final IOException e) {
                
                CraftTweakerAPI.LOGGER.error(() -> "Could not find or create folder " + file.getParent() + ", aborting formatting task!", e);
            }
            
            try(final BufferedWriter writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8, StandardOpenOption.CREATE)) {
                
                writer.write(format);
            } catch(final IOException e) {
                
                CraftTweakerAPI.LOGGER.error("Could not write formatted files", e);
            }
        });
    }
    
}
