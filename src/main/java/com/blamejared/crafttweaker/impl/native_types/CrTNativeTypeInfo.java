package com.blamejared.crafttweaker.impl.native_types;

import com.blamejared.crafttweaker_annotations.annotations.NativeConstructor;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class CrTNativeTypeInfo {
    
    private final String craftTweakerName;
    private final Class<?> vanillaClass;
    private final List<Class<?>[]> constructors;
    
    public CrTNativeTypeInfo(Class<?> vanillaClass, String craftTweakerName, List<Class<?>[]> constructors) {
        this.craftTweakerName = craftTweakerName;
        this.vanillaClass = vanillaClass;
        this.constructors = constructors;
    }
    
    public String getCraftTweakerName() {
        return craftTweakerName;
    }
    
    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(o == null || getClass() != o.getClass())
            return false;
        
        CrTNativeTypeInfo that = (CrTNativeTypeInfo) o;
        
        return craftTweakerName.equals(that.craftTweakerName);
    }
    
    @Override
    public int hashCode() {
        return craftTweakerName.hashCode();
    }
    
    public Class<?> getVanillaClass() {
        return vanillaClass;
    }
    
    public List<Class<?>[]> getConstructors() {
        return constructors;
    }
}
