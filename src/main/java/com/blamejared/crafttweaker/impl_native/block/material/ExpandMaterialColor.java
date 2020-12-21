package com.blamejared.crafttweaker.impl_native.block.material;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.block.material.MaterialColor;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@NativeTypeRegistration(value = MaterialColor.class, zenCodeName = "crafttweaker.api.block.material.MCMaterialColor")
public class ExpandMaterialColor {
    
    @ZenCodeType.Method
    public static int getMapColor(MaterialColor internal, int index) {
        return internal.getMapColor(index);
    }
    
    
}
