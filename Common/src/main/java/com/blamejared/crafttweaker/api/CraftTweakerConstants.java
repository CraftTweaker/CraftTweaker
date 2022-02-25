package com.blamejared.crafttweaker.api;

import net.minecraft.resources.ResourceLocation;

import java.io.File;
import java.util.UUID;

public class CraftTweakerConstants {
    
    public static final String MOD_ID = "crafttweaker";
    public static final String MOD_NAME = "CraftTweaker";
    public static final UUID CRAFTTWEAKER_UUID = UUID.nameUUIDFromBytes(MOD_ID.getBytes());
    public static final String SCRIPTS_DIRECTORY = "./scripts";
    @Deprecated(forRemoval = true)
    public static final File SCRIPT_DIR = new File(SCRIPTS_DIRECTORY); // TODO("See above, use Path, and go through platform")
    public static final String LOG_PATH = "logs/crafttweaker.log";
    
    public static final String INIT_LOADER_NAME = "initialize";
    public static final String DEFAULT_LOADER_NAME = "crafttweaker";
    
    /**
     * This is not the mod version, this is specifically for the network!!
     */
    public static final String NETWORK_VERSION = "1.0.0";
    
    public static final ResourceLocation RELOAD_LISTENER_SOURCE_ID = CraftTweakerConstants.rl("reload_listener");
    public static final ResourceLocation CLIENT_RECIPES_UPDATED_SOURCE_ID = CraftTweakerConstants.rl("client_recipes_updated");
    
    public static ResourceLocation rl(String path) {
        
        return new ResourceLocation(MOD_ID, path);
    }
    
}
