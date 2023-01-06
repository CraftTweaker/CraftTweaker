package com.blamejared.crafttweaker.impl.script.scriptrun;

import org.apache.logging.log4j.Logger;
import org.openzen.zencode.java.logger.ScriptingEngineLogger;
import org.openzen.zencode.shared.CompileException;
import org.openzen.zencode.shared.SourceFile;
import org.openzen.zenscript.validator.ValidationLogEntry;

import java.util.OptionalInt;
import java.util.function.Function;

sealed class ScriptRunLogger implements ScriptingEngineLogger permits GameTestScriptRunLogger {
    
    private final Logger logger;
    private final Function<SourceFile, OptionalInt> priority;
    
    ScriptRunLogger(final Logger logger, final Function<SourceFile, OptionalInt> priorityGetter) {
        
        this.logger = logger;
        this.priority = priorityGetter;
    }
    
    @Override
    public void logCompileException(final CompileException exception) {
        
        this.logger.error("Error while compiling scripts: ", exception);
    }
    
    @Override
    public void info(final String message) {
        
        this.logger.info(message);
    }
    
    @Override
    public void debug(final String message) {
        
        this.logger.debug(message);
    }
    
    @Override
    public void trace(final String message) {
        
        this.logger.trace(message);
    }
    
    @Override
    public void warning(final String message) {
        
        this.logger.warn(message);
    }
    
    @Override
    public void error(final String message) {
        
        this.logger.error(message);
    }
    
    @Override
    public void throwingErr(final String message, final Throwable throwable) {
        
        this.logger.error("Error while running scripts:", throwable);
    }
    
    @Override
    public void throwingWarn(final String message, final Throwable throwable) {
        
        this.logger.warn("Warning while running scripts:", throwable);
    }
    
    @Override
    public void logSourceFile(final SourceFile file) {
        
        this.logger.info(
                "Loading file '{}'{}",
                file.getFilename(),
                this.priority.apply(file).stream().mapToObj(it -> " with priority " + it).findFirst().orElse("")
        );
    }
    
    @Override
    public void logValidationError(final ValidationLogEntry errorEntry) {
        
        this.logger.error("{}: {}", errorEntry.position, errorEntry.message);
    }
    
    @Override
    public void logValidationWarning(final ValidationLogEntry warningEntry) {
        
        this.logger.warn("{}: {}", warningEntry.position, warningEntry.message);
    }
    
}
