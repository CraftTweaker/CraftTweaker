package com.blamejared.crafttweaker.api.zencode;


import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.game.Game;
import com.blamejared.crafttweaker.api.mod.Mods;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister(loaders = CraftTweakerConstants.ALL_LOADERS_MARKER)
@ZenCodeType.Name("crafttweaker.api.Globals")
public final class CraftTweakerGlobals {
    
    @ZenRegister
    @ZenCodeType.Name("crafttweaker.api.CtGlobals")
    public static final class CraftTweakerOnlyGlobals {
        
        @ZenCodeGlobals.Global("game")
        public static final Game GAME = new Game();
        
        @ZenCodeGlobals.Global("loadedMods")
        public static final Mods MODS = new Mods();
        
    }
    
    @ZenCodeGlobals.Global
    public static void println(String msg) {
        
        CraftTweakerAPI.LOGGER.info(msg);
    }
    
    @ZenCodeGlobals.Global
    public static void print(String msg) {
        
        println(msg);
    }
    
}