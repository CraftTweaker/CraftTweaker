package com.blamejared.crafttweaker.impl.plugin.core;

import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.blamejared.crafttweaker.api.zencode.impl.IScriptLoadSource;

import java.util.Collection;

public interface IPluginRegistryAccess {
    
    void registerLoaders(final Collection<IScriptLoader> loader);
    
    void registerLoadSources(final Collection<IScriptLoadSource> sources);
    
}
