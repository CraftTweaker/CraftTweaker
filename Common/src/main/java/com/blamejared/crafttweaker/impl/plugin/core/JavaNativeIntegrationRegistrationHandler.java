package com.blamejared.crafttweaker.impl.plugin.core;

import com.blamejared.crafttweaker.api.natives.NativeTypeInfo;
import com.blamejared.crafttweaker.api.plugin.IJavaNativeIntegrationRegistrationHandler;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.ZenTypeInfo;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanMaps;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

class JavaNativeIntegrationRegistrationHandler implements IJavaNativeIntegrationRegistrationHandler {
    
    record NativeClassRequest(String loader, NativeTypeInfo info) {}
    
    record ZenClassRequest(String loader, Class<?> clazz, ZenTypeInfo info) {}
    
    private final List<IPreprocessor> preprocessors;
    private final List<NativeClassRequest> nativeClassRequests;
    private final Object2BooleanMap<ZenClassRequest> zenClassRequests;
    
    private JavaNativeIntegrationRegistrationHandler() {
        
        this.preprocessors = new ArrayList<>();
        this.nativeClassRequests = new ArrayList<>();
        this.zenClassRequests = new Object2BooleanOpenHashMap<>();
    }
    
    static JavaNativeIntegrationRegistrationHandler of(final Consumer<IJavaNativeIntegrationRegistrationHandler> consumer) {
        
        final JavaNativeIntegrationRegistrationHandler handler = new JavaNativeIntegrationRegistrationHandler();
        consumer.accept(handler);
        return handler;
    }
    
    @Override
    public void registerNativeType(final String loader, final Class<?> clazz, final NativeTypeInfo info) {
        
        this.nativeClassRequests.add(new NativeClassRequest(loader, info));
        this.zenClassRequests.put(new ZenClassRequest(loader, clazz, ZenTypeInfo.from(info)), false);
    }
    
    @Override
    public void registerZenClass(final String loader, final Class<?> clazz, final ZenTypeInfo info) {
        
        this.zenClassRequests.put(new ZenClassRequest(loader, clazz, info), false);
    }
    
    @Override
    public void registerGlobalsIn(final String loader, final Class<?> clazz, final ZenTypeInfo info) {
        
        if(info.kind() == ZenTypeInfo.TypeKind.EXPANSION) {
            throw new IllegalArgumentException("Unable to register globals for " + clazz.getName() + " because it is an expansion: use a normal class instead");
        }
        final ZenClassRequest request = new ZenClassRequest(loader, clazz, info);
        if(!this.zenClassRequests.containsKey(request)) {
            throw new IllegalArgumentException("Unable to register globals for " + clazz.getName() + " because it is unknown: register it first");
        }
        this.zenClassRequests.put(request, true);
    }
    
    @Override
    public void registerPreprocessor(final IPreprocessor preprocessor) {
        
        // TODO("Checks")
        this.preprocessors.add(preprocessor);
    }
    
    List<IPreprocessor> preprocessors() {
        
        return Collections.unmodifiableList(this.preprocessors);
    }
    
    List<NativeClassRequest> nativeClassRequests() {
        
        return Collections.unmodifiableList(this.nativeClassRequests);
    }
    
    Object2BooleanMap<ZenClassRequest> zenClassRequests() {
        
        return Object2BooleanMaps.unmodifiable(this.zenClassRequests);
    }
    
}
