package com.blamejared.crafttweaker.api.zencode.impl.registry.wrapper;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nullable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class WrapperRegistryEntry {
    
    private final Class<?> wrappedClass;
    private final Class<?> wrapperClass;
    
    public WrapperRegistryEntry(Class<?> wrappedClass, Class<?> wrapperClass) {
        
        this.wrappedClass = wrappedClass;
        this.wrapperClass = wrapperClass;
    }
    
    public Class<?> getWrappedClass() {
        
        return wrappedClass;
    }
    
    public Class<?> getWrapperClass() {
        
        return wrapperClass;
    }
    
    public String getWrapperClassZCName() {
        
        return getWrapperClass().getAnnotation(ZenCodeType.Name.class).value();
    }
    
    @Nullable
    public MethodHandle getWrapperHandle() {
        
        try {
            final Constructor<?> constructor = getWrapperClass().getConstructor(getWrappedClass());
            return MethodHandles.lookup().unreflectConstructor(constructor);
        } catch(NoSuchMethodException | IllegalAccessException e) {
            CraftTweakerAPI.logWarning("Could not find WrapperHandle for " + getWrapperClass());
        }
        
        //TODO: Also check static methods with proper signature
        return null;
    }
    
    @Nullable
    public MethodHandle getUnwrapperHandle() {
        
        try {
            final Method getInternalMethod = getWrapperClass().getMethod("getInternal");
            return MethodHandles.lookup().unreflect(getInternalMethod);
        } catch(NoSuchMethodException | IllegalAccessException e) {
            CraftTweakerAPI.logWarning("Could not find UnwrapperHandle for " + getWrapperClass());
        }
        //TODO: Also check other methods that return the proper type
        return null;
    }
    
}
