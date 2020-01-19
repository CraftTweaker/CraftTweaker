package com.blamejared.crafttweaker.api.logger;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.logger.GroupLogger;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.IZSLogger;
import org.openzen.zencode.java.ZenCodeType;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Base class used to interface with the crafttweaker.log file and other loggers (such as the player logger).
 * @docParam this logger
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.ILogger")
@Document("vanilla/api/logger/ILogger")
public interface ILogger extends IZSLogger {
    
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
    
    void log(LogLevel level, String message, boolean prefix);
    
    
    default void log(LogLevel level, String message) {
        log(level, message, true);
    }
    
    /**
     * Logs an info message.
     *
     * @param message message to be logged.
     * @docParam message "message"
     */
    @ZenCodeType.Method
    default void info(String message) {
        log(LogLevel.INFO, message);
    }
    
    /**
     * Logs a debug message.
     *
     * @param message message to be logged.
     * @docParam message "message"
     */
    @ZenCodeType.Method
    default void debug(String message) {
        log(LogLevel.DEBUG, message);
    }
    
    /**
     * Logs a warning message.
     *
     * @param message message to be logged.
     * @docParam message "message"
     */
    @ZenCodeType.Method
    default void warning(String message) {
        log(LogLevel.WARNING, message);
    }
    
    /**
     * Logs an error message.
     *
     * @param message message to be logged.
     * @docParam message "message"
     */
    @ZenCodeType.Method
    default void error(String message) {
        log(LogLevel.ERROR, message);
    }
    
    default void throwingErr(String message, Throwable throwable) {
        error(message);
        throwable.printStackTrace(errorStream);
        log(LogLevel.ERROR, errorStream.getValue(), false);
    }
    
    default void throwingWarn(String message, Throwable throwable) {
        warning(message);
        throwable.printStackTrace(errorStream);
        log(LogLevel.WARNING, errorStream.getValue(), false);
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
