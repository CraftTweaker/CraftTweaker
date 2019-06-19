package com.blamejared.crafttweaker.api.logger;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Base class used to interface with the crafttweaker.log file and other loggers.
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.ILogger")
public interface ILogger {
    
    @ZenCodeType.Method
    void info(String message);
    
    @ZenCodeType.Method
    void debug(String message);
    
    @ZenCodeType.Method
    void warning(String message);
    
    @ZenCodeType.Method
    void error(String message);
    
    void error(String message, Throwable throwable);
}
