package com.blamejared.crafttweaker.api.plugin;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import net.minecraft.resources.ResourceLocation;

public interface ICraftTweakerPlugin {
    
    String REGISTRATION_CHANNEL_ID = CraftTweakerConstants.MOD_ID + ":plugin";
    
    ResourceLocation id();
    
    default void registerLoaders(final ILoaderRegistrationHandler handler) {}
    
    default void registerBracketParsers(final IBracketParserRegistrationHandler handler) {}
    
    default void manageJavaNativeIntegration(final IJavaNativeIntegrationRegistrationHandler handler) {}
    
    default void registerListeners(final IListenerRegistrationHandler handler) {}
    
}
