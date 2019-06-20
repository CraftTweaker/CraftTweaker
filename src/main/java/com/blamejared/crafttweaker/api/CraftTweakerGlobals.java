package com.blamejared.crafttweaker.api;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import org.openzen.zencode.java.*;

@ZenRegister
public class CraftTweakerGlobals {
    
    @ZenCodeGlobals.Global
    public static void println(String msg) {
        CraftTweakerAPI.logger.info(msg);
    }
    
}