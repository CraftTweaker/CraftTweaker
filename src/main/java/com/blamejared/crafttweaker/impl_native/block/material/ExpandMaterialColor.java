package com.blamejared.crafttweaker.impl_native.block.material;

import com.blamejared.crafttweaker_annotations.annotations.NativeExpansion;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.DocumentAsType;
import net.minecraft.block.material.MaterialColor;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@DocumentAsType
@NativeExpansion(MaterialColor.class)
public class ExpandMaterialColor {
    
    @ZenCodeType.Method
    public static int getMapColor(MaterialColor internal, int index) {
        return internal.getMapColor(index);
    }
    
    
}
