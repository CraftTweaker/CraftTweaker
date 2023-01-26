package com.blamejared.crafttweaker.api.recipe.component;

import com.blamejared.crafttweaker.api.fluid.CTFluidIngredient;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;

import java.util.Objects;

/**
 * Holds equality checkers for the various {@linkplain BuiltinRecipeComponents builtin components}.
 *
 * <p>These checkers are provided separately in case creators of new {@link IRecipeComponent}s want to reuse them.</p>
 *
 * @since 10.0.0
 */
public final class ForgeRecipeComponentEqualityCheckers {
    
    public static boolean areFluidIngredientsEqual(final CTFluidIngredient a, final CTFluidIngredient b) {
        
        return Objects.equals(a, b) || (a.contains(b) && b.contains(a));
    }
    
    public static boolean areFluidStacksEqual(final IFluidStack a, final IFluidStack b) {
        
        return a.containsOther(b);
    }
    
}
