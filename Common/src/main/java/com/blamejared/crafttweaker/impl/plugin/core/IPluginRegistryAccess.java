package com.blamejared.crafttweaker.impl.plugin.core;

import com.blamejared.crafttweaker.api.zencode.IScriptLoader;

import java.util.Collection;

public interface IPluginRegistryAccess {
    
    void registerLoaders(final Collection<IScriptLoader> loader);
    
}
