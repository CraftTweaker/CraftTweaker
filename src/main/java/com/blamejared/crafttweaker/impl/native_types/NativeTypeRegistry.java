package com.blamejared.crafttweaker.impl.native_types;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Holds a listing of all registered vanilla types.
 * These types are referred to as "native" types in the CrT context
 */
public class NativeTypeRegistry {
    
    private final Map<Class<?>, CrTNativeTypeInfo> nativeTypeInfos = new HashMap<>();
    
    public void addNativeType(Class<?> vanillaClass, String crtName) {
        if(nativeTypeInfos.containsKey(vanillaClass) && !nativeTypeInfos.get(vanillaClass)
                .getCraftTweakerName()
                .equals(crtName)) {
            final String format = "Trying to register vanilla class '%s' twice with different names: '%s' and '%s'";
            final CrTNativeTypeInfo existingNativeTypeInfo = nativeTypeInfos.get(vanillaClass);
            CraftTweakerAPI.logError(format, existingNativeTypeInfo.getCraftTweakerName(), crtName);
        } else {
            nativeTypeInfos.put(vanillaClass, new CrTNativeTypeInfo(vanillaClass, crtName));
        }
    }
    
    public boolean hasInfoFor(Class<?> clazz) {
        return nativeTypeInfos.containsKey(clazz);
    }
    
    public String getCrTNameFor(Class<?> clazz) {
        return nativeTypeInfos.get(clazz).getCraftTweakerName();
    }
    
    public Collection<CrTNativeTypeInfo> getNativeTypeInfos() {
        return nativeTypeInfos.values();
    }
}
