package com.blamejared.crafttweaker.impl_native.block.material;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.block.material.MaterialColor;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/block/material/MCMaterialColor")
@NativeTypeRegistration(value = MaterialColor.class, zenCodeName = "crafttweaker.api.block.material.MCMaterialColor")
public class ExpandMaterialColor {
    
    /**
     * Gets the Integer value of the colour of this material color on a map.
     *
     * @param index the index to get the colour for.
     *
     * @return the Integer value of the map colour
     *
     * @docParam index 1
     */
    @ZenCodeType.Method
    public static int getMapColor(MaterialColor internal, int index) {
        //TODO 1.17 change this to call the MaterialColor method when it isn't client only.
        int i;
        switch(index) {
            case 0:
                i = 180;
                break;
            default:
                i = 220;
                break;
            case 2:
                i = 255;
                break;
            case 3:
                i = 135;
                break;
        }
        
        int j = (internal.colorValue >> 16 & 255) * i / 255;
        int k = (internal.colorValue >> 8 & 255) * i / 255;
        int l = (internal.colorValue & 255) * i / 255;
        return -16777216 | l << 16 | k << 8 | j;
    }
    
    
}
