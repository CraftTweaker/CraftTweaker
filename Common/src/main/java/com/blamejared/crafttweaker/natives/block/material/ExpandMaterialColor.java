package com.blamejared.crafttweaker.natives.block.material;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.material.MaterialColor;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/block/material/MaterialColor")
@NativeTypeRegistration(value = MaterialColor.class, zenCodeName = "crafttweaker.api.block.material.MaterialColor")
public class ExpandMaterialColor {
    
    @ZenCodeType.Method
    public static int calculateRGBColor(MaterialColor internal, int index) {
        
        return internal.calculateRGBColor(index);
    }
    
}
