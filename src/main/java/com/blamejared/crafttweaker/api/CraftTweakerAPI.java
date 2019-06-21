package com.blamejared.crafttweaker.api;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.logger.*;
import org.openzen.zencode.java.ZenCodeGlobals;

import java.io.File;

@ZenRegister
public class CraftTweakerAPI {
    
    public static final File SCRIPT_DIR = new File("scripts");
    
    @ZenCodeGlobals.Global
    public static GroupLogger logger;
    
    public static void setupLoggers() {
        logger = new GroupLogger();
        logger.addLogger(new FileLogger(new File("logs/crafttweaker.log")));
        //TODO maybe post an event to collect a bunch of loggers? not sure if it will be used much
    }
    
}
