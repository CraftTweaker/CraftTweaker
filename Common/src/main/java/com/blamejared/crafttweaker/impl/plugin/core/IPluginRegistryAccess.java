package com.blamejared.crafttweaker.impl.plugin.core;

import com.blamejared.crafttweaker.api.natives.NativeTypeInfo;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.blamejared.crafttweaker.api.zencode.ZenTypeInfo;
import com.blamejared.crafttweaker.api.zencode.impl.IScriptLoadSource;

import java.util.Collection;

public interface IPluginRegistryAccess {
    
    void registerLoaders(final Collection<IScriptLoader> loader);
    
    void registerLoadSources(final Collection<IScriptLoadSource> sources);
    
    void registerPreprocessor(final IPreprocessor preprocessor);
    
    void registerNativeType(final IScriptLoader loader, final NativeTypeInfo info);
    
    void registerZenType(final IScriptLoader loader, final Class<?> clazz, final ZenTypeInfo info, final boolean globals);
    
}
