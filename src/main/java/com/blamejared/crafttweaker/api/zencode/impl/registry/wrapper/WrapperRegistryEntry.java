package com.blamejared.crafttweaker.api.zencode.impl.registry.wrapper;

import com.blamejared.crafttweaker.api.*;
import org.openzen.zencode.java.*;

import javax.annotation.*;
import java.lang.invoke.*;
import java.lang.reflect.*;

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
