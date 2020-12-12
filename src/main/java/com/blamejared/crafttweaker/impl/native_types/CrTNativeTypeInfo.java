package com.blamejared.crafttweaker.impl.native_types;

public class CrTNativeTypeInfo {
    private final String craftTweakerName;
    private final Class<?> vanillaClass;
    
    public CrTNativeTypeInfo(Class<?> vanillaClass, String craftTweakerName) {
        this.craftTweakerName = craftTweakerName;
        this.vanillaClass = vanillaClass;
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
}
