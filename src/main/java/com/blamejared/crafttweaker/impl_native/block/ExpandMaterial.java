package com.blamejared.crafttweaker.impl_native.block;

import com.blamejared.crafttweaker.api.annotations.NativeExpansion;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@NativeExpansion(Material.class)
public class ExpandMaterial {
    
    /**
     * Indicate if the material is opaque
     */
    @ZenCodeType.Method
    public static boolean isOpaque(Material internal) {
        return internal.isOpaque();
    }
    
    
    /**
     * Retrieves the color index of the block. This is is the same color used by vanilla maps to represent this block.
     */
    @ZenCodeType.Method
    public static MaterialColor getColor(Material internal) {
        return internal.getColor();
    }
    
    
    /**
     * Returns true if the block is a considered solid. This is true by default.
     */
    @ZenCodeType.Method
    public static boolean isSolid(Material internal) {
        return internal.isSolid();
    }
    
    
    /**
     * Returns if the block can burn or not.
     */
    @ZenCodeType.Method
    public static boolean isFlammable(Material internal) {
        return internal.isFlammable();
    }
    
    
    /**
     * Returns if blocks of these materials are liquids.
     */
    @ZenCodeType.Method
    public static boolean isLiquid(Material internal) {
        return internal.isLiquid();
    }
    
    /**
     * Returns whether the material can be replaced by other blocks when placed - eg snow, vines and tall grass.
     */
    @ZenCodeType.Method
    public static boolean isReplaceable(Material internal) {
        return internal.isReplaceable();
    }
    
    
    /**
     * Returns if this material is considered solid or not
     */
    @ZenCodeType.Method
    public static boolean blocksMovement(Material internal) {
        return internal.blocksMovement();
    }
}
