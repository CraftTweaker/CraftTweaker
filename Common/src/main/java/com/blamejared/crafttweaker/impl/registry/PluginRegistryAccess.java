package com.blamejared.crafttweaker.impl.registry;

import com.blamejared.crafttweaker.api.natives.NativeTypeInfo;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.IScriptLoader;
import com.blamejared.crafttweaker.api.zencode.ZenTypeInfo;
import com.blamejared.crafttweaker.api.zencode.impl.IScriptLoadSource;
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
        this.registries.zenClassRegistry().fillLoaderData(loader);
    }
    
    @Override
    public void registerLoadSources(final Collection<IScriptLoadSource> sources) {
        
        this.registries.loadSourceRegistry().registerLoadSources(sources);
    }
    
    @Override
    public void registerPreprocessor(final IPreprocessor preprocessor) {
        
        this.registries.preprocessorRegistry().register(preprocessor);
    }
    
    @Override
    public void registerNativeType(final IScriptLoader loader, final NativeTypeInfo info) {
        
        this.registries.zenClassRegistry().registerNativeType(loader, info);
    }
    
    @Override
    public void registerZenType(final IScriptLoader loader, final Class<?> clazz, final ZenTypeInfo info, final boolean globals) {
        
        this.registries.zenClassRegistry().registerZenType(loader, clazz, info, globals);
    }
    
}
