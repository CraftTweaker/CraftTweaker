package com.blamejared.crafttweaker.api.tag.expand;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.fluid.CTFluidIngredient;
import com.blamejared.crafttweaker.api.tag.type.KnownTag;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.world.level.material.Fluid;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.List;

/**
 * This expansion specifically targets FluidTags on Forge.
 */
@ZenRegister
@Document("vanilla/api/tag/ExpandFluidTagForge")
@ZenCodeType.Expansion("crafttweaker.api.tag.type.KnownTag<crafttweaker.api.fluid.Fluid>")
public class ExpandFluidTagForge {
    
    @ZenCodeType.Method
    @ZenCodeType.Caster(implicit = true)
    public static CTFluidIngredient asFluidIngredient(KnownTag<Fluid> internal) {
        
        return new CTFluidIngredient.FluidTagWithAmountIngredient(internal.withAmount(1));
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Operator(ZenCodeType.OperatorType.OR)
    public static CTFluidIngredient asList(KnownTag<Fluid> internal, CTFluidIngredient other) {
        
        List<CTFluidIngredient> elements = new ArrayList<>();
        elements.add(asFluidIngredient(internal));
        elements.add(other);
        return new CTFluidIngredient.CompoundFluidIngredient(elements);
    }
    
}
