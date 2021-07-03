package com.blamejared.crafttweaker.impl.native_types;

import java.lang.reflect.*;
import java.util.*;

public class CrTNativeTypeInfo {
    
    private final String craftTweakerName;
    private final Class<?> vanillaClass;
    private final Map<String, CrTNativeExecutableRefs> methods;
    
    public CrTNativeTypeInfo(Class<?> vanillaClass, String craftTweakerName, Map<String, CrTNativeExecutableRefs> methods1) {
        
        this.craftTweakerName = craftTweakerName;
        this.vanillaClass = vanillaClass;
        this.methods = methods1;
    }
    
    public String getCraftTweakerName() {
        
        return craftTweakerName;
    }
    
    @Override
    public boolean equals(Object o) {
        
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        
        CrTNativeTypeInfo that = (CrTNativeTypeInfo) o;
        
        return getCraftTweakerName().equals(that.getCraftTweakerName());
    }
    
    @Override
    public int hashCode() {
        
        return getCraftTweakerName().hashCode();
    }
    
    public Class<?> getVanillaClass() {
        
        return vanillaClass;
    }
    
    public Optional<CrTNativeExecutableRef> getMethod(Constructor<?> method) {
        return getMethod("<init>", method.getParameterTypes());
    }
    
    public Optional<CrTNativeExecutableRef> getMethod(Method method) {
        
        return getMethod(method.getName(), method.getParameterTypes());
    }
    
    private Optional<CrTNativeExecutableRef> getMethod(String name, Class<?>[] parameterTypes) {
        
        if(!methods.containsKey(name)) {
            return Optional.empty();
        }
        
        return methods.get(name).getForSignature(parameterTypes);
    }
}
