package com.blamejared.crafttweaker.api.logger;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import org.openzen.zencode.java.ZenCodeType;

import java.io.*;

/**
 * Base class used to interface with the crafttweaker.log file and other loggers.
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.ILogger")
public interface ILogger {
    
    void log(LogLevel level, String message, boolean prefix);
    
    
    default void log(LogLevel level, String message) {
        log(level, message, true);
    }
    
    @ZenCodeType.Method
    default void info(String message) {
        log(LogLevel.INFO, message);
    }
    
    @ZenCodeType.Method
    default void debug(String message) {
        log(LogLevel.DEBUG, message);
    }
    
    @ZenCodeType.Method
    default void warning(String message) {
        log(LogLevel.WARNING, message);
    }
    
    @ZenCodeType.Method
    default void error(String message) {
        log(LogLevel.ERROR, message);
    }
    
    default void error(String message, Throwable throwable) {
        error(message);
        StringPrintStream s = new StringPrintStream();
        throwable.printStackTrace(errorStream);
        log(LogLevel.ERROR, errorStream.getValue(), false);
        
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
