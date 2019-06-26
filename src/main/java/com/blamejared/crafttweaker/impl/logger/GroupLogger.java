package com.blamejared.crafttweaker.impl.logger;

import com.blamejared.crafttweaker.api.logger.*;

import java.util.*;

public class GroupLogger implements ILogger {
    
    private final List<ILogger> subLoggers = new ArrayList<>();
    //TODO keep a list of all messages that are logged, when a new logger is added, log those messages to that logger, for players logging in etc?
    
    public void addLogger(ILogger logger) {
        this.subLoggers.add(logger);
    }
    
    public List<ILogger> getSubLoggers() {
        return subLoggers;
    }

    @Override
    public void setLogLevel(LogLevel logLevel) {
        for (ILogger subLogger : subLoggers) {
            subLogger.setLogLevel(logLevel);
        }
    }

    @Override
    public LogLevel getLogLevel() {
        return subLoggers.stream().map(ILogger::getLogLevel).min(LogLevel::compareTo).orElse(LogLevel.DEBUG);
    }

    @Override
    public void log(LogLevel level, String message, boolean prefix) {
        for(ILogger logger : getSubLoggers()) {
            logger.log(level, message, prefix);
        }
    }
    
    @Override
    public void log(LogLevel level, String message) {
        for(ILogger logger : getSubLoggers()) {
            logger.log(level, message);
        }
    }
    
    @Override
    public void info(String message) {
        for(ILogger logger : getSubLoggers()) {
            logger.info(message);
        }
    }
    
    @Override
    public void debug(String message) {
        for(ILogger logger : getSubLoggers()) {
            logger.debug(message);
        }
    }
    
    @Override
    public void warning(String message) {
        for(ILogger logger : getSubLoggers()) {
            logger.warning(message);
        }
    }
    
    @Override
    public void error(String message) {
        for(ILogger logger : getSubLoggers()) {
            logger.error(message);
        }
    }
    
    @Override
    public void throwingErr(String message, Throwable throwable) {
        for(ILogger logger : getSubLoggers()) {
            logger.throwingErr(message, throwable);
        }
    }
}
