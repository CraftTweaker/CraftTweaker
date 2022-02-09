package com.blamejared.crafttweaker.impl.registry;

import com.blamejared.crafttweaker.api.zencode.IScriptLoader;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

// TODO("Everything")
public final class LoaderRegistry {
    
    private final Map<String, IScriptLoader> loaders = new HashMap<>();
    
    public IScriptLoader find(final String name) {
        
        return this.loaders.computeIfAbsent(name, it -> {
            throw new IllegalArgumentException("No loader with name '" + it + "' registered; are you too early?");
        });
    }
    
    public Collection<IScriptLoader> getAllLoaders() {
        
        return Collections.unmodifiableCollection(this.loaders.values());
    }
    
}
