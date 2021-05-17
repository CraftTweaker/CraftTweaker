package com.blamejared.crafttweaker.impl.tag.expansions;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.CTFluidIngredient;
import com.blamejared.crafttweaker.impl.tag.MCTag;
import com.blamejared.crafttweaker.impl.tag.MCTagWithAmount;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.fluid.Fluid;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ZenRegister
@Document("vanilla/api/tags/ExpandFluidTag")
@ZenCodeType.Expansion("crafttweaker.api.tag.MCTag<crafttweaker.api.fluid.MCFluid>")
public class ExpandFluidTag {
    
    @ZenCodeType.Method
    @ZenCodeType.Caster(implicit = true)
    public static CTFluidIngredient asIngredient(MCTag<Fluid> _this) {
        
        return new CTFluidIngredient.FluidTagWithAmountIngredient(new MCTagWithAmount<>(_this, 1));
    }
    
    @ZenCodeType.Operator(ZenCodeType.OperatorType.OR)
    public static CTFluidIngredient asList(MCTag<Fluid> _this, CTFluidIngredient other) {
        
        List<CTFluidIngredient> elements = new ArrayList<>();
        elements.add(asIngredient(_this));
        elements.add(other);
        return new CTFluidIngredient.CompoundFluidIngredient(elements);
    }
    
}
