package com.blamejared.crafttweaker.impl.native_types;

import java.util.List;

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
        
        return getCraftTweakerName().equals(that.getCraftTweakerName());
    }
    
    @Override
    public int hashCode() {
        return getCraftTweakerName().hashCode();
    }
    
    public Class<?> getVanillaClass() {
        return vanillaClass;
    }
    
    public List<Class<?>[]> getConstructors() {
        return constructors;
    }
}
