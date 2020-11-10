package com.blamejared.crafttweaker.api.zencode.impl.registry.wrapper;

import com.blamejared.crafttweaker.*;
import com.blamejared.crafttweaker.api.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
import com.google.common.collect.*;
import cpw.mods.modlauncher.api.*;
import net.minecraftforge.fml.common.*;
import org.objectweb.asm.*;

import javax.annotation.*;
import java.util.*;

/**
 * Holds information on Runtime classes with {@link ZenWrapper} Annotation
 */
public class WrapperRegistry {
    
    private final BiMap<Class<?>, Class<?>> wrapperClassByWrappedClass = HashBiMap.create();
    private final Map<Class<?>, WrapperRegistryEntry> wrapperEntryByWrappedClass = new HashMap<>();
    
    
    public void addType(Type type) {
        try {
            addClass(findClass(type.getClassName()));
        } catch(Exception e) {
            CraftTweakerAPI.logDebug("Could not initialize wrapper for type " + type.getClassName() + ": " + e);
        }
    }
    
    @Nonnull
    private Class<?> findClass(String forName) throws ClassNotFoundException {
        return Class.forName(forName, false, CraftTweaker.class.getClassLoader());
    }
    
    private void addClass(Class<?> wrapperClass) throws ClassNotFoundException {
        final ZenWrapper annotation = wrapperClass.getAnnotation(ZenWrapper.class);
        final Class<?> wrappedClass;
        {
            final String wrappedClassName = annotation.wrappedClass();
            final String wrappedClassRemapped = ObfuscationReflectionHelper.remapName(INameMappingService.Domain.CLASS, wrappedClassName);
            wrappedClass = findClass(wrappedClassRemapped);
        }
        wrapperClassByWrappedClass.put(wrappedClass, wrapperClass);
        final WrapperRegistryEntry value = new WrapperRegistryEntry(wrappedClass, wrapperClass);
        wrapperEntryByWrappedClass.put(wrappedClass, value);
    }
    
    @Nullable
    public WrapperRegistryEntry getEntryFor(Class<?> registrySuperType) {
        if(wrapperEntryByWrappedClass.containsKey(registrySuperType)) {
            return wrapperEntryByWrappedClass.get(registrySuperType);
        }
        
        final Class<?> inverse = wrapperClassByWrappedClass.inverse().get(registrySuperType);
        if(wrapperEntryByWrappedClass.containsKey(inverse)) {
            return wrapperEntryByWrappedClass.get(inverse);
        }
        
        return null;
    }
}
