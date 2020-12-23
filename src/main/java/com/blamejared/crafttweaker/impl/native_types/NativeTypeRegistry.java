package com.blamejared.crafttweaker.impl.native_types;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker_annotations.annotations.NativeConstructor;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Holds a listing of all registered vanilla types.
 * These types are referred to as "native" types in the CrT context
 */
public class NativeTypeRegistry {
    
    private final Map<Class<?>, CrTNativeTypeInfo> nativeTypeInfos = new HashMap<>();
    
    private static Class<?>[] convertConstructorToClassArray(NativeConstructor nativeConstructor) {
        return Arrays.stream(nativeConstructor.value())
                .map(NativeConstructor.ConstructorParameter::type)
                .toArray(Class<?>[]::new);
    }
    
    public void addNativeType(NativeTypeRegistration registration) {
        final Class<?> vanillaClass = registration.value();
        final String crtName = registration.zenCodeName();
        final List<Class<?>[]> constructors = getNativeConstructors(registration);
        
        if(nativeTypeInfos.containsKey(vanillaClass) && !nativeTypeInfos.get(vanillaClass)
                .getCraftTweakerName()
                .equals(crtName)) {
            final String format = "Trying to register vanilla class '%s' twice with different names: '%s' and '%s'";
            final CrTNativeTypeInfo existingNativeTypeInfo = nativeTypeInfos.get(vanillaClass);
            CraftTweakerAPI.logError(format, existingNativeTypeInfo.getCraftTweakerName(), crtName);
        } else {
            nativeTypeInfos.put(vanillaClass, new CrTNativeTypeInfo(vanillaClass, crtName, constructors));
        }
    }
    
    private List<Class<?>[]> getNativeConstructors(NativeTypeRegistration registration) {
        return Arrays.stream(registration.constructors())
                .map(NativeTypeRegistry::convertConstructorToClassArray)
                .collect(Collectors.toList());
    }
    
    public boolean hasInfoFor(Class<?> clazz) {
        return nativeTypeInfos.containsKey(clazz);
    }
    
    public String getCrTNameFor(Class<?> clazz) {
        return getTypeInfoFor(clazz).getCraftTweakerName();
    }
    
    public Collection<CrTNativeTypeInfo> getNativeTypeInfos() {
        return nativeTypeInfos.values();
    }
    
    public CrTNativeTypeInfo getTypeInfoFor(Class<?> clazz) {
        return nativeTypeInfos.get(clazz);
    }
}
