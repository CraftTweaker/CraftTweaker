package com.blamejared.crafttweaker.impl.logger;

import com.blamejared.crafttweaker.api.logger.*;

import java.util.*;

public class GroupLogger implements ILogger {
    
    private final List<ILogger> subLoggers = new ArrayList<>();
    
    
    public void addLogger(ILogger logger) {
        this.subLoggers.add(logger);
    }
    
    public List<ILogger> getSubLoggers() {
        return subLoggers;
    }
    
    @Override
    public void setLogLevel(LogLevel logLevel) {
        for(ILogger subLogger : subLoggers) {
            subLogger.setLogLevel(logLevel);
        }
    }
    
    @Override
    public LogLevel getLogLevel() {
        return subLoggers.stream().map(ILogger::getLogLevel).min(LogLevel::compareTo).orElse(LogLevel.DEBUG);
    }
    
    @Override
    public void log(LogLevel level, String message, boolean prefix, Object... formats) {
        for(ILogger logger : getSubLoggers()) {
            logger.log(level, message, prefix, formats);
        }
    }
    
    @Override
    public void log(LogLevel level, String message, Object... formats) {
        for(ILogger logger : getSubLoggers()) {
            logger.log(level, message, formats);
        }
    }
    
    @Override
    public void info(String message, Object... formats) {
        for(ILogger logger : getSubLoggers()) {
            logger.info(message, formats);
        }
    }
    
    @Override
    public void debug(String message, Object... formats) {
        for(ILogger logger : getSubLoggers()) {
            logger.debug(message, formats);
        }
    }
    
    @Override
    public void warning(String message, Object... formats) {
        for(ILogger logger : getSubLoggers()) {
            logger.warning(message, formats);
        }
    }
    
    @Override
    public void error(String message, Object... formats) {
        for(ILogger logger : getSubLoggers()) {
            logger.error(message, formats);
        }
    }
    
    @Override
    public void throwing(String message, Throwable throwable, Object... formats) {
        for(ILogger logger : getSubLoggers()) {
            logger.throwing(message, throwable, formats);
        }
    }
}
