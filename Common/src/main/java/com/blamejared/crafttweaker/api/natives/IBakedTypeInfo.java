package com.blamejared.crafttweaker.api.natives;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Optional;

public interface IBakedTypeInfo {
    
    String zenName();
    
    Class<?> nativeClass();
    
    Optional<IExecutableReferenceInfo> findMethod(final Constructor<?> method);
    
    Optional<IExecutableReferenceInfo> findMethod(final Method method);
    
}
