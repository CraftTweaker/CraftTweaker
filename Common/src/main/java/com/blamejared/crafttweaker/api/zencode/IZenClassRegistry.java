package com.blamejared.crafttweaker.api.zencode;

import com.blamejared.crafttweaker.api.natives.NativeTypeRegistry;
import com.google.common.collect.BiMap;
import com.google.common.collect.Multimap;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IZenClassRegistry {
    
    interface IClassData {
        
        List<Class<?>> registeredClasses();
        
        BiMap<String, Class<?>> globals();
        
        BiMap<String, Class<?>> classes();
        
        Multimap<String, Class<?>> expansions();
        
    }
    
    boolean isRegistered(final IScriptLoader loader, final Class<?> clazz);
    
    Optional<String> getNameFor(final IScriptLoader loader, final Class<?> clazz);
    
    <T> List<Class<? extends T>> getImplementationsOf(final IScriptLoader loader, final Class<T> target);
    
    IClassData getClassData(final IScriptLoader loader);
    
    List<Class<?>> getClassesInPackage(final IScriptLoader loader, final String packageName);
    
    List<Class<?>> getGlobalsInPackage(final IScriptLoader loader, final String packageName);
    
    Set<String> getRootPackages(final IScriptLoader loader);
    
    NativeTypeRegistry getNativeTypeRegistry(final IScriptLoader loader); // TODO("Interface?")
    
    boolean isBlacklisted(final Class<?> clazz);
    
}
