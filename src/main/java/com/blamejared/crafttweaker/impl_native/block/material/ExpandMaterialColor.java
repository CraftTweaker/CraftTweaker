package com.blamejared.crafttweaker.impl_native.block.material;

import com.blamejared.crafttweaker.api.annotations.NativeExpansion;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import net.minecraft.block.material.MaterialColor;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@NativeExpansion(MaterialColor.class)
public class ExpandMaterialColor {
    
    @ZenCodeType.Method
    public static int getMapColor(MaterialColor internal, int index) {
        return internal.getMapColor(index);
    }
    
    
}
