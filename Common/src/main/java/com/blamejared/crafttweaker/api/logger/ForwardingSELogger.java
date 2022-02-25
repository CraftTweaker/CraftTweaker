package com.blamejared.crafttweaker.api.logger;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.impl.preprocessor.PriorityPreprocessor;
import org.openzen.zencode.java.logger.ScriptingEngineLogger;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zencode.shared.SourceFile;
import org.openzen.zenscript.validator.ValidationLogEntry;

public enum ForwardingSELogger implements ScriptingEngineLogger {
    INSTANCE;
    
    @Override
    public void logCompileException(CompileException exception) {
        
        error(exception.getMessage());
    }
    
    @Override
    public void info(String message) {
        
        CraftTweakerAPI.LOGGER.info(message);
    }
    
    @Override
    public void debug(String message) {
        
        CraftTweakerAPI.LOGGER.debug(message);
    }
    
    @Override
    public void trace(String message) {
        
        CraftTweakerAPI.LOGGER.trace(message);
    }
    
    @Override
    public void warning(String message) {
        
        CraftTweakerAPI.LOGGER.warn(message);
    }
    
    @Override
    public void error(String message) {
        
        CraftTweakerAPI.LOGGER.error(message);
    }
    
    @Override
    public void throwingErr(String message, Throwable throwable) {
        
        CraftTweakerAPI.LOGGER.error(message, throwable);
    }
    
    @Override
    public void throwingWarn(String message, Throwable throwable) {
        
        CraftTweakerAPI.LOGGER.warn(message, throwable);
    }
    
    @Override
    public void logSourceFile(SourceFile file) {
        
        if(file instanceof final SourceFilePreprocessed sourceFilePreprocessed) {
            final int priority = Integer.parseInt(sourceFilePreprocessed.getMatches()
                    .get(PriorityPreprocessor.INSTANCE)
                    .get(0)
                    .getContent());
            
            CraftTweakerAPI.LOGGER.info("Loading file: '{}' with priority: {}", file.getFilename(), priority);
        } else {
            CraftTweakerAPI.LOGGER.info("Loading file: {}", file.getFilename());
        }
    }
    
    @Override
    public void logValidationError(ValidationLogEntry errorEntry) {
        
        CraftTweakerAPI.LOGGER.error(errorEntry.position + " " + errorEntry.message);
    }
    
    @Override
    public void logValidationWarning(ValidationLogEntry warningEntry) {
        
        CraftTweakerAPI.LOGGER.error(warningEntry.position + " " + warningEntry.message);
    }
    
}
