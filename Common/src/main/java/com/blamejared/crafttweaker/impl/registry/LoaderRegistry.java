package com.blamejared.crafttweaker.impl.registry;

import com.blamejared.crafttweaker.api.zencode.IScriptLoader;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

final class LoaderRegistry {
    
    private final Map<String, IScriptLoader> loaders = new HashMap<>();
    
    void registerLoaders(final Collection<IScriptLoader> loaders) {
    
        if(!this.loaders.isEmpty()) {
            throw new IllegalStateException("Loaders have already been registered");
        }
        loaders.forEach(it -> this.loaders.put(it.name(), it));
    }
    
    IScriptLoader find(final String name) {
        
        return this.loaders.computeIfAbsent(name, it -> {
            throw new IllegalArgumentException("No loader with name '" + it + "' registered; are you too early?");
        });
    }
    
    Collection<IScriptLoader> getAllLoaders() {
        
        return Collections.unmodifiableCollection(this.loaders.values());
    }
    
}
