package com.blamejared.crafttweaker.impl.block.material;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.ZenWrapper;
import net.minecraft.block.material.MaterialColor;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.block.material.MCMaterialColor")
@Document("vanilla/api/block/material/MCMaterialColor")
@ZenWrapper(wrappedClass = "net.minecraft.block.material.MaterialColor", conversionMethodFormat = "%s.getInternal()", displayStringFormat = "%s.toString()")
public class MCMaterialColor {
    private final MaterialColor internal;

    public MCMaterialColor(MaterialColor internal){
        this.internal = internal;
    }

    public MaterialColor getInternal() {
        return this.internal;
    }

    @ZenCodeType.Method
    public int getMapColor(int index) {
        return internal.getMapColor(index);
    }


}
