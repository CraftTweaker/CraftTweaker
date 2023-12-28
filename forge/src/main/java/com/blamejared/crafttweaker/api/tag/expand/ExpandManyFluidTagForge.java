package com.blamejared.crafttweaker.api.tag.expand;

import com.blamejared.crafttweaker.api.fluid.CTFluidIngredient;
import com.blamejared.crafttweaker.api.tag.type.KnownTag;
import com.blamejared.crafttweaker.api.util.Many;
import net.minecraft.world.level.material.Fluid;

import java.util.ArrayList;
import java.util.List;

/**
 * * @deprecated Use {@link ExpandManyFluidTag} instead.
 */
@Deprecated(forRemoval = true)
public class ExpandManyFluidTagForge {
    
    @Deprecated(forRemoval = true)
    public static CTFluidIngredient asFluidIngredient(Many<KnownTag<Fluid>> internal) {
        
        return new CTFluidIngredient.FluidTagWithAmountIngredient(internal);
    }
    
    @Deprecated(forRemoval = true)
    public static CTFluidIngredient asList(Many<KnownTag<Fluid>> internal, CTFluidIngredient other) {
        
        List<CTFluidIngredient> elements = new ArrayList<>();
        elements.add(asFluidIngredient(internal));
        elements.add(other);
        return new CTFluidIngredient.CompoundFluidIngredient(elements);
    }
    
}
