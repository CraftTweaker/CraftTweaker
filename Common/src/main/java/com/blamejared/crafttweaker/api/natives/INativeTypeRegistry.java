package com.blamejared.crafttweaker.api.natives;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Optional;

public interface INativeTypeRegistry {
    
    Optional<String> getZenNameFor(final Class<?> clazz);
    
    Collection<IBakedTypeInfo> getBakedTypeInfo();
    
    Optional<IBakedTypeInfo> getBakedTypeInfoFor(final Class<?> clazz);
    
    Optional<IExecutableReferenceInfo> getExecutableReferenceInfoFor(final Constructor<?> constructor);
    
    Optional<IExecutableReferenceInfo> getExecutableReferenceInfoFor(final Method method);
    
}
