package com.blamejared.crafttweaker.api.tag.expand;

import com.blamejared.crafttweaker.api.fluid.CTFluidIngredient;
import com.blamejared.crafttweaker.api.tag.type.KnownTag;
import net.minecraft.world.level.material.Fluid;

import java.util.ArrayList;
import java.util.List;

/**
 * This expansion specifically targets FluidTags on Forge.
 *
 * @deprecated Use {@link ExpandFluidTag} instead.
 */
@Deprecated(forRemoval = true)
public class ExpandFluidTagForge {
    
    @Deprecated(forRemoval = true)
    public static CTFluidIngredient asFluidIngredient(KnownTag<Fluid> internal) {
        
        return new CTFluidIngredient.FluidTagWithAmountIngredient(internal.withAmount(1));
    }
    
    @Deprecated(forRemoval = true)
    public static CTFluidIngredient asList(KnownTag<Fluid> internal, CTFluidIngredient other) {
        
        List<CTFluidIngredient> elements = new ArrayList<>();
        elements.add(asFluidIngredient(internal));
        elements.add(other);
        return new CTFluidIngredient.CompoundFluidIngredient(elements);
    }
    
}
