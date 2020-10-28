package com.blamejared.crafttweaker.api;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import org.openzen.zencode.java.ZenCodeGlobals;

@ZenRegister
public class CraftTweakerGlobals {
    
    @ZenCodeGlobals.Global
    public static void println(String msg) {
        CraftTweakerAPI.logger.info(msg);
    }
    
    @ZenCodeGlobals.Global
    public static void print(String msg) {
        println(msg);
    }

    @ZenCodeGlobals.Global
    public static boolean isNull(Object obj) {
        return obj == null;
    }

    @ZenCodeGlobals.Global
    public static boolean nonNull(Object obj) {
        return obj != null;
    }
}