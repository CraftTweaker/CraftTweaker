package com.blamejared.crafttweaker.impl.registry;

import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunModuleConfigurator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

final class ScriptRunModuleConfiguratorRegistry {
    
    private final Map<IScriptLoader, IScriptRunModuleConfigurator> configurators = new HashMap<>();
    
    void register(final IScriptLoader loader, final IScriptRunModuleConfigurator configurator) {
        
        final IScriptRunModuleConfigurator other = this.configurators.get(loader);
        if(other != null) {
            throw new IllegalArgumentException("A configurator for the loader " + loader.name() + " is already registered: " + other);
        }
        
        this.configurators.put(loader, configurator);
    }
    
    void verify(final Collection<IScriptLoader> loaders) {
        
        final Collection<IScriptLoader> unregisteredLoaders = loaders.stream()
                .filter(it -> !this.configurators.containsKey(it))
                .toList();
        if(!unregisteredLoaders.isEmpty()) {
            throw new IllegalStateException("Missing configurator for loaders " + unregisteredLoaders);
        }
    }
    
    IScriptRunModuleConfigurator find(final IScriptLoader loader) {
        
        return Objects.requireNonNull(this.configurators.get(loader));
    }
    
}
