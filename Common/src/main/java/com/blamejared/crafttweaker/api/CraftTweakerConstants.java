package com.blamejared.crafttweaker.api;

import net.minecraft.resources.ResourceLocation;

import java.io.File;
import java.util.UUID;

public class CraftTweakerConstants {
    
    public static final String MOD_ID = "crafttweaker";
    public static final String MOD_NAME = "CraftTweaker";
    public static final UUID CRAFTTWEAKER_UUID = UUID.nameUUIDFromBytes(MOD_ID.getBytes());
    public static final File SCRIPT_DIR = new File("scripts");
    public static final String LOG_PATH = "logs/crafttweaker.log";
    
    public static final String DEFAULT_LOADER_NAME = "crafttweaker";
    
    /**
     * This is not the mod version, this is specifically for the network!!
     */
    public static final String NETWORK_VERSION = "1.0.0";
    
    
    public static ResourceLocation rl(String path) {
        
        return new ResourceLocation(MOD_ID, path);
    }
    
}
