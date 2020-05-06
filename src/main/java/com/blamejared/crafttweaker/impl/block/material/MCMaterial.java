package com.blamejared.crafttweaker.impl.block.material;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.brackets.*;
import com.blamejared.crafttweaker.impl.block.material.MCMaterialColor;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.ZenWrapper;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.block.material.MCMaterial")
@Document("vanilla/api/block/material/MCMaterial")
@ZenWrapper(wrappedClass = "net.minecraft.block.material.Material", displayStringFormat = "%s.toString()", creationMethodFormat = "new MCMaterial(%s, \"unknown\")")
public class MCMaterial implements CommandStringDisplayable {
    private final Material internal;
    private final String name;
    
    public MCMaterial(Material internal, String name){
        this.internal = internal;
        this.name = name;
    }

    public Material getInternal() {
        return this.internal;
    }

    /**
     * Indicate if the material is opaque
     */
    @ZenCodeType.Method
    public boolean isOpaque() {
        return internal.isOpaque();
    }


    /**
     * Retrieves the color index of the block. This is is the same color used by vanilla maps to represent this block.
     */
    @ZenCodeType.Method
    public MCMaterialColor getColor() {
        return new MCMaterialColor(internal.getColor());
    }


    /**
     * Returns true if the block is a considered solid. This is true by default.
     */
    @ZenCodeType.Method
    public boolean isSolid() {
        return internal.isSolid();
    }


    /**
     * Returns if the block can burn or not.
     */
    @ZenCodeType.Method
    public boolean isFlammable() {
        return internal.isFlammable();
    }


    /**
     * Returns if blocks of these materials are liquids.
     */
    @ZenCodeType.Method
    public boolean isLiquid() {
        return internal.isLiquid();
    }


    /**
     * Returns true if the material can be harvested without a tool (or with the wrong tool)
     */
    @ZenCodeType.Method
    public boolean isToolNotRequired() {
        return internal.isToolNotRequired();
    }


    /**
     * Returns whether the material can be replaced by other blocks when placed - eg snow, vines and tall grass.
     */
    @ZenCodeType.Method
    public boolean isReplaceable() {
        return internal.isReplaceable();
    }


    /**
     * Returns if this material is considered solid or not
     */
    @ZenCodeType.Method
    public boolean blocksMovement() {
        return internal.blocksMovement();
    }
    
    
    @Override
    public String getCommandString() {
        return "<blockmaterial:" + getName() + ">";
    }
    
    public String getName() {
        return this.name;
    }
}
