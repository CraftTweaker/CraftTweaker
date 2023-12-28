package com.blamejared.crafttweaker.impl.plugin.core;

import com.blamejared.crafttweaker.api.zencode.IScriptLoadSource;
import net.minecraft.resources.ResourceLocation;

record LoadSource(ResourceLocation id) implements IScriptLoadSource {
    
    @Override
    public String toString() {
        
        return this.id().toString();
    }
    
}
