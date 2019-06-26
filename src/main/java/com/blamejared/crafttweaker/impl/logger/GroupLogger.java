package com.blamejared.crafttweaker.impl.logger;

import com.blamejared.crafttweaker.api.logger.*;

import java.util.*;

public class GroupLogger implements ILogger {
    
    private final List<ILogger> subLoggers = new ArrayList<>();
    //TODO keep a list of all messages that are logged, when a new logger is added, log those messages to that logger, for players logging in etc?
    private final List<LogMessage> previousMessages = new ArrayList<>();
    
    public void addLogger(ILogger logger) {
        this.subLoggers.add(logger);
        previousMessages.forEach(logMessage -> logger.log(logMessage.level, logMessage.message, logMessage.prefix));
    }
    
    public List<LogMessage> getPreviousMessages() {
        return previousMessages;
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
    public void log(LogLevel level, String message, boolean prefix) {
        for(ILogger logger : getSubLoggers()) {
            logger.log(level, message, prefix);
        }
        previousMessages.add(new LogMessage(level, message, prefix));
    }
    
    @Override
    public void log(LogLevel level, String message) {
        for(ILogger logger : getSubLoggers()) {
            logger.log(level, message);
        }
        previousMessages.add(new LogMessage(level, message, false));
    }
    
    @Override
    public void info(String message) {
        for(ILogger logger : getSubLoggers()) {
            logger.info(message);
        }
        previousMessages.add(new LogMessage(LogLevel.INFO, message, false));
    }
    
    @Override
    public void debug(String message) {
        for(ILogger logger : getSubLoggers()) {
            logger.debug(message);
        }
        previousMessages.add(new LogMessage(LogLevel.DEBUG, message, false));
    }
    
    @Override
    public void warning(String message) {
        for(ILogger logger : getSubLoggers()) {
            logger.warning(message);
        }
        previousMessages.add(new LogMessage(LogLevel.WARNING, message, false));
    }
    
    @Override
    public void error(String message) {
        for(ILogger logger : getSubLoggers()) {
            logger.error(message);
        }
        previousMessages.add(new LogMessage(LogLevel.ERROR, message, false));
    }
    
    @Override
    public void throwingWarn(String message, Throwable throwable) {
        for(ILogger logger : getSubLoggers()) {
            logger.throwingWarn(message, throwable);
        }
        previousMessages.add(new LogMessage(LogLevel.WARNING, message, false));
    }
    
    @Override
    public void throwingErr(String message, Throwable throwable) {
        for(ILogger logger : getSubLoggers()) {
            logger.throwingErr(message, throwable);
        }
        previousMessages.add(new LogMessage(LogLevel.ERROR, message, false));
    }
    
    
    private class LogMessage {
        
        private final LogLevel level;
        private final String message;
        private final boolean prefix;
        
        public LogMessage(LogLevel level, String message, boolean prefix) {
            this.level = level;
            this.message = message;
            this.prefix = prefix;
        }
        
        public String getMessage() {
            return message;
        }
        
        public LogLevel getLevel() {
            return level;
        }
        
        public boolean isPrefix() {
            return prefix;
        }
    }
}
