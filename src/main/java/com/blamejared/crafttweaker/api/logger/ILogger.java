package com.blamejared.crafttweaker.api.logger;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.logger.*;
import org.openzen.zencode.java.ZenCodeType;

import java.io.*;

/**
 * Base class used to interface with the crafttweaker.log file and other loggers.
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.ILogger")
public interface ILogger {
    
    /**
     * <p>
     * Sets the last level that will be logged<br>
     * E.g. a logLevel of INFO means that everything below
     * it (in this case{@code [DEBUG]}) will <strong>not</strong> be logged
     * </p>
     * <p>
     * In case of using SubLoggers (e.g. {@link GroupLogger}) propagates the
     * method call to <strong>all</strong> SubLoggers
     * </p>
     */
    void setLogLevel(LogLevel logLevel);
    
    /**
     * <p>
     * Returns the last level that will be logged<br>
     * E.g. a logLevel of INFO means that everything below
     * it (in this case{@code [DEBUG]}) will <strong>not</strong> be logged
     * </p>
     * <p>
     * In case of using SubLoggers (e.g. {@link GroupLogger}) returns the last level
     * at which <strong>anything</strong> will be logged
     * </p>
     */
    LogLevel getLogLevel();
    
    void log(LogLevel level, String message, boolean prefix, Object... formats);
    
    
    default void log(LogLevel level, String message, Object... formats) {
        log(level, message, true, formats);
    }
    
    @ZenCodeType.Method
    default void info(String message, Object... formats) {
        log(LogLevel.INFO, message, formats);
    }
    
    @ZenCodeType.Method
    default void debug(String message, Object... formats) {
        log(LogLevel.DEBUG, message, formats);
    }
    
    @ZenCodeType.Method
    default void warning(String message, Object... formats) {
        log(LogLevel.WARNING, message, formats);
    }
    
    @ZenCodeType.Method
    default void error(String message, Object... formats) {
        log(LogLevel.ERROR, message, formats);
    }
    
    default void throwing(String message, Throwable throwable, Object... formats) {
        error(message, formats);
        StringPrintStream s = new StringPrintStream();
        throwable.printStackTrace(errorStream);
        log(LogLevel.ERROR, errorStream.getValue(), false, formats);
        
    }
    
    StringPrintStream errorStream = new StringPrintStream();
    
    class StringPrintStream extends PrintStream {
        
        public StringPrintStream() {
            super(new ByteArrayOutputStream());
        }
        
        public String getValue() {
            return out.toString();
        }
    }
}
