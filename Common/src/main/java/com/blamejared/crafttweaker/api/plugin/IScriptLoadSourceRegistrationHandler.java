package com.blamejared.crafttweaker.api.plugin;

import net.minecraft.resources.ResourceLocation;

public interface IScriptLoadSourceRegistrationHandler {
    
    void registerLoadSource(final ResourceLocation id);
    
}
