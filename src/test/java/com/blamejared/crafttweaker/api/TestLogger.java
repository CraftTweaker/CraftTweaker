package com.blamejared.crafttweaker.api;

import com.blamejared.crafttweaker.api.logger.ILogger;
import com.blamejared.crafttweaker.api.logger.LogLevel;

public class TestLogger implements ILogger {
    
    @Override
    public void setLogLevel(LogLevel logLevel) {
        //NoOp
    }
    
    @Override
    public LogLevel getLogLevel() {
        
        return LogLevel.DEBUG;
    }
    
    @Override
    public void log(LogLevel level, String message, boolean prefix) {
        
        System.out.printf("[%s] %s%n", level, message);
    }
    
}
