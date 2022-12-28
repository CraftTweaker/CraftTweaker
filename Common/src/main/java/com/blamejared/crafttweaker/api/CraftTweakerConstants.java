package com.blamejared.crafttweaker.api;

import net.minecraft.resources.ResourceLocation;

import java.util.UUID;

public final class CraftTweakerConstants {
    
    public static final String MOD_ID = "crafttweaker";
    public static final String MOD_NAME = "CraftTweaker";
    public static final String SCRIPTS_DIRECTORY = "./scripts";
    
    public static final String LOG_NAME = "CRT_LOG_FILE";
    public static final String LOG_PATH = "logs/crafttweaker.log";
    
    public static final String ALL_LOADERS_MARKER = "*";
    public static final String INIT_LOADER_NAME = "initialize";
    public static final String DEFAULT_LOADER_NAME = "crafttweaker";
    public static final String TAGS_LOADER_NAME = "tags";
    
    /**
     * This is not the mod version, this is specifically for the network!!
     */
    public static final String NETWORK_VERSION = "1.0.0";
    
    public static final UUID CRAFTTWEAKER_UUID = UUID.nameUUIDFromBytes(MOD_ID.getBytes());
    
    public static final ResourceLocation RELOAD_LISTENER_SOURCE_ID = CraftTweakerConstants.rl("reload_listener");
    public static final ResourceLocation CLIENT_RECIPES_UPDATED_SOURCE_ID = CraftTweakerConstants.rl("client_recipes_updated");
    
    public static ResourceLocation rl(String path) {
        
        return new ResourceLocation(MOD_ID, path);
    }
    
}
