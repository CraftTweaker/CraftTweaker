package com.blamejared.crafttweaker.api.zencode;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import net.minecraft.resources.ResourceLocation;

@FunctionalInterface
public interface IScriptLoadSource {
    
    static IScriptLoadSource find(final ResourceLocation id) {
        
        return CraftTweakerAPI.getRegistry().findLoadSource(id);
    }
    
    ResourceLocation id();
    
}
