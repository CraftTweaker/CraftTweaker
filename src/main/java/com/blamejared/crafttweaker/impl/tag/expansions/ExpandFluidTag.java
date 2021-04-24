package com.blamejared.crafttweaker.impl.tag.expansions;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.CrTFluidIngredient;
import com.blamejared.crafttweaker.impl.tag.MCTag;
import com.blamejared.crafttweaker.impl.tag.MCTagWithAmount;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.fluid.Fluid;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/tags/ExpandFluidTag")
@ZenCodeType.Expansion("crafttweaker.api.tag.MCTag<crafttweaker.api.fluid.MCFluid>")
public class ExpandFluidTag {
    
    @ZenCodeType.Method
    @ZenCodeType.Caster(implicit = true)
    public static CrTFluidIngredient asIngredient(MCTag<Fluid> _this) {
        
        return new CrTFluidIngredient.TagWithAmountFluidIngredient(new MCTagWithAmount<>(_this, 1));
    }
    
}
