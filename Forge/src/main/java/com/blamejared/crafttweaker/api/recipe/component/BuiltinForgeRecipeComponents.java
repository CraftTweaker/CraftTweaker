package com.blamejared.crafttweaker.api.recipe.component;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.fluid.CTFluidIngredient;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Holds all {@link IRecipeComponent}s that CraftTweaker makes available directly that rely on Forge specific classes.
 *
 * @since 10.0.0
 */
public final class BuiltinForgeRecipeComponents {
    
    /**
     * Holds {@link IRecipeComponent}s representing inputs for the various recipes.
     *
     * <p>The amount of time required is not considered an input, rather an intrinsic factor and thus is processing
     * data. On the contrary, anything that impedes the beginning of the execution, such as energy or experience
     * requirements represent inputs for the recipe.</p>
     *
     * @since 10.0.0
     */
    public static final class Input {
        
        public static final IRecipeComponent<CTFluidIngredient> FLUID_INGREDIENTS = IRecipeComponent.composite(
                CraftTweakerConstants.rl("input/fluid_ingredients"),
                new TypeToken<>() {},
                ForgeRecipeComponentEqualityCheckers::areFluidIngredientsEqual,
                ingredient -> ingredient instanceof CTFluidIngredient.CompoundFluidIngredient cfi ? cfi.getElements() : List.of(ingredient),
                items -> items.size() < 1 ? CTFluidIngredient.EMPTY.get() : items.stream()
                        .reduce(CTFluidIngredient::asCompound)
                        .orElseThrow()
        );
        
        private Input() {}
        
    }
    
    public static final class Output {
    
        public static final IRecipeComponent<IFluidStack> FLUIDS = IRecipeComponent.simple(
                CraftTweakerConstants.rl("output/fluids"),
                new TypeToken<>() {},
                ForgeRecipeComponentEqualityCheckers::areFluidStacksEqual
        );
        
        private Output() {}
        
    }
    
}
