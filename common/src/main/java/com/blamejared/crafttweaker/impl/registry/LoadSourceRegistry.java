package com.blamejared.crafttweaker.impl.registry;

import com.blamejared.crafttweaker.api.zencode.IScriptLoadSource;
import net.minecraft.resources.ResourceLocation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

final class LoadSourceRegistry {
    
    private final Map<ResourceLocation, IScriptLoadSource> loadSources = new HashMap<>();
    
    void registerLoadSources(final Collection<IScriptLoadSource> loaders) {
        
        if(!this.loadSources.isEmpty()) {
            throw new IllegalStateException("Load sources have already been registered");
        }
        loaders.forEach(it -> this.loadSources.put(it.id(), it));
    }
    
    IScriptLoadSource get(final ResourceLocation id) {
        
        return this.loadSources.computeIfAbsent(id, it -> {
            throw new IllegalArgumentException("No load source with id '" + it + "' registered; are you too early?");
        });
    }
    
}
