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
            
            wrappedClass = getClassCheckInner(wrappedClassRemapped);
        }
        wrapperClassByWrappedClass.put(wrappedClass, wrapperClass);
        final WrapperRegistryEntry value = new WrapperRegistryEntry(wrappedClass, wrapperClass);
        wrapperEntryByWrappedClass.put(wrappedClass, value);
    }
    
    /**
     * Tries to ger the class based on the given name.
     * If it cannot find it, will check if it can find an inner class with the name and so on.
     * <p>
     * <p>
     * {@code "my.super.clazz.someInnerClazz"} will first check for a toplevel class of that name.
     * Then for {@code "my.super.clazz$someInnerClazz"}, then {@code "my.super$clazz$someInnerClazz"} and so on.
     * <p>
     * If it cannot find any, will throw the CNF with the original name
     */
    private Class<?> getClassCheckInner(String name) throws ClassNotFoundException {
        try {
            return findClass(name);
        } catch(ClassNotFoundException ex) {
            final int index = name.lastIndexOf('.');
            if(index < 0) {
                throw ex;
            }
            
            final StringBuilder stringBuilder = new StringBuilder(name);
            stringBuilder.setCharAt(index, '$');
            try {
                return getClassCheckInner(stringBuilder.toString());
            } catch(ClassNotFoundException ignored) {
                throw ex;
            }
        }
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
