package com.blamejared.crafttweaker.api.plugin;

import com.blamejared.crafttweaker.api.natives.NativeTypeInfo;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.ZenTypeInfo;

public interface IJavaNativeIntegrationRegistrationHandler {
    
    void registerNativeType(final String loader, final Class<?> clazz, final NativeTypeInfo info);
    
    void registerZenClass(final String loader, final Class<?> clazz, final ZenTypeInfo info);
    
    void registerGlobalsIn(final String loader, final Class<?> clazz, final ZenTypeInfo info);
    
    void registerPreprocessor(final IPreprocessor preprocessor);
    
}
