package com.blamejared.crafttweaker.impl.registry;

import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.blamejared.crafttweaker.impl.plugin.core.IPluginRegistryAccess;

import java.util.Collection;

@SuppressWarnings("ClassCanBeRecord")
final class PluginRegistryAccess implements IPluginRegistryAccess {
    
    private final Registries registries;
    
    PluginRegistryAccess(final Registries registries) {
        
        this.registries = registries;
    }
    
    @Override
    public void registerLoaders(final Collection<IScriptLoader> loader) {
        
        this.registries.loaderRegistry().registerLoaders(loader);
    }
    
}
