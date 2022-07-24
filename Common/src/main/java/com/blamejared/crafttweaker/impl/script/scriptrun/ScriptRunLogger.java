package com.blamejared.crafttweaker.impl.script.scriptrun;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import org.openzen.zencode.java.logger.ScriptingEngineLogger;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zencode.shared.SourceFile;
import org.openzen.zenscript.validator.ValidationLogEntry;

import java.util.OptionalInt;
import java.util.function.Function;

sealed class ScriptRunLogger implements ScriptingEngineLogger permits GameTestScriptRunLogger {
    
    private final Function<SourceFile, OptionalInt> priority;
    
    ScriptRunLogger(final Function<SourceFile, OptionalInt> priorityGetter) {
        
        this.priority = priorityGetter;
    }
    
    @Override
    public void logCompileException(final CompileException exception) {
        
        CraftTweakerAPI.LOGGER.error("Error while compiling scripts: ", exception);
    }
    
    @Override
    public void info(final String message) {
        
        CraftTweakerAPI.LOGGER.info(message);
    }
    
    @Override
    public void debug(final String message) {
        
        CraftTweakerAPI.LOGGER.debug(message);
    }
    
    @Override
    public void trace(final String message) {
        
        CraftTweakerAPI.LOGGER.trace(message);
    }
    
    @Override
    public void warning(final String message) {
        
        CraftTweakerAPI.LOGGER.warn(message);
    }
    
    @Override
    public void error(final String message) {
        
        CraftTweakerAPI.LOGGER.error(message);
    }
    
    @Override
    public void throwingErr(final String message, final Throwable throwable) {
        
        CraftTweakerAPI.LOGGER.error("Error while running scripts:", throwable);
    }
    
    @Override
    public void throwingWarn(final String message, final Throwable throwable) {
        
        CraftTweakerAPI.LOGGER.warn("Warning while running scripts:", throwable);
    }
    
    @Override
    public void logSourceFile(final SourceFile file) {
        
        CraftTweakerAPI.LOGGER.info(
                "Loading file '{}'{}",
                file.getFilename(),
                this.priority.apply(file).stream().mapToObj(it -> " with priority " + it).findFirst().orElse("")
        );
    }
    
    @Override
    public void logValidationError(final ValidationLogEntry errorEntry) {
        
        CraftTweakerAPI.LOGGER.error("{}: {}", errorEntry.position, errorEntry.message);
    }
    
    @Override
    public void logValidationWarning(final ValidationLogEntry warningEntry) {
        
        CraftTweakerAPI.LOGGER.warn("{}: {}", warningEntry.position, warningEntry.message);
    }
    
}
