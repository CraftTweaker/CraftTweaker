package com.blamejared.crafttweaker.impl.native_types;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker_annotations.annotations.NativeConstructor;
import com.blamejared.crafttweaker_annotations.annotations.NativeMethod;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
    
    public void addNativeType(NativeTypeRegistration registration, NativeMethod[] nativeMethods) {
        
        final Class<?> vanillaClass = registration.value();
        final String crtName = registration.zenCodeName();
        final Map<String, CrTNativeExecutableRefs> executables = getNativeExecutables(registration, nativeMethods);
        
        
        if(nativeTypeInfos.containsKey(vanillaClass) && !nativeTypeInfos.get(vanillaClass)
                .getCraftTweakerName()
                .equals(crtName)) {
            final String format = "Trying to register vanilla class '%s' twice with different names: '%s' and '%s'";
            final CrTNativeTypeInfo existingNativeTypeInfo = nativeTypeInfos.get(vanillaClass);
            CraftTweakerAPI.logError(format, vanillaClass.getCanonicalName(), existingNativeTypeInfo.getCraftTweakerName(), crtName);
        } else {
            nativeTypeInfos.put(vanillaClass, new CrTNativeTypeInfo(vanillaClass, crtName, executables));
        }
    }
    
    private Map<String, CrTNativeExecutableRefs> getNativeExecutables(NativeTypeRegistration registration, NativeMethod[] nativeMethods) {
        
        final HashMap<String, CrTNativeExecutableRefs> result = new HashMap<>();
        
        for(NativeConstructor constructor : registration.constructors()) {
            final Class<?>[] classes = convertConstructorToClassArray(constructor);
            result.computeIfAbsent("<init>", i -> new CrTNativeExecutableRefs())
                    .createForSignature(classes)
                    .withConstructorAnnotation();
        }
        
        for(NativeMethod nativeMethod : nativeMethods) {
            result.computeIfAbsent(nativeMethod.name(), i -> new CrTNativeExecutableRefs())
                    .createForSignature(nativeMethod.parameters())
                    .withGetter(nativeMethod.getterName())
                    .withSetter(nativeMethod.setterName())
                    .withMethodAnnotation();
        }
        
        return result;
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
    
    public Optional<CrTNativeExecutableRef> getMethodInfoFor(Constructor<?> constructor) {
        
        if(hasInfoFor(constructor.getDeclaringClass())) {
            return getTypeInfoFor(constructor.getDeclaringClass()).getMethod(constructor);
        }
        return Optional.empty();
    }
    
    public Optional<CrTNativeExecutableRef> getMethodInfoFor(Method method) {
        
        if(hasInfoFor(method.getDeclaringClass())) {
            return getTypeInfoFor(method.getDeclaringClass()).getMethod(method);
        }
        return Optional.empty();
    }
    
}
