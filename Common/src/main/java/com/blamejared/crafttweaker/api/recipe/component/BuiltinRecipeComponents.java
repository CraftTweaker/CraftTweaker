package com.blamejared.crafttweaker.api.recipe.component;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.type.IIngredientEmpty;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.MirrorAxis;
import com.google.gson.reflect.TypeToken;
import it.unimi.dsi.fastutil.ints.IntIntPair;

import java.util.Arrays;
import java.util.function.BiFunction;

/**
 * Holds all {@link IRecipeComponent}s that CraftTweaker makes available directly.
 *
 * @since 10.0.0
 */
public final class BuiltinRecipeComponents {
    
    /**
     * Holds {@link IRecipeComponent}s that simply encode recipe metadata.
     *
     * <p>The metadata might be the level required for a level-based crafting table, the group in the recipe book, or
     * other elements. <strong>Name</strong> is not part of metadata.</p>
     *
     * @since 10.0.0
     */
    public static final class Metadata {
        
        public static final IRecipeComponent<String> GROUP = IRecipeComponent.simple(
                CraftTweakerConstants.rl("metadata/group"),
                new TypeToken<>() {},
                Object::equals
        );
        
        // TODO("No")
        public static final IRecipeComponent<MirrorAxis> MIRROR_AXIS = IRecipeComponent.simple(
                CraftTweakerConstants.rl("metadata/mirror_axis"),
                new TypeToken<>() {},
                Object::equals
        );
        
        public static final IRecipeComponent<IntIntPair> SHAPE_SIZE_2D = IRecipeComponent.simple(
                CraftTweakerConstants.rl("metadata/shape_size_2d"),
                new TypeToken<>() {},
                Object::equals
        );
        
        private Metadata() {}
        
    }
    
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
        
        public static final IRecipeComponent<IIngredient> INGREDIENTS = IRecipeComponent.composite(
                CraftTweakerConstants.rl("input/ingredients"),
                new TypeToken<>() {},
                RecipeComponentEqualityCheckers::areIngredientsEqual,
                ingredient -> Arrays.asList(ingredient.getItems()),
                items -> items.size() < 1 ? IIngredientEmpty.getInstance() : items.stream()
                        .reduce(IIngredient::or)
                        .orElseThrow()
        );
        
        private Input() {}
        
    }
    
    /**
     * Holds {@link IRecipeComponent}s representing data used by recipes during their processing.
     *
     * <p>Examples might be the amount of time required, the consumption rate of a certain resource such as energy, or
     * processing failure chance. The chance for various outputs is not part of processing data. The amount of energy or
     * experience required to begin the process is also not part of processing, rather input. Processing solely
     * indicates things that happen during the recipe execution process.</p>
     *
     * @since 10.0.0
     */
    public static final class Processing {
        
        // TODO("")
        public static final IRecipeComponent<BiFunction<IItemStack, IItemStack, IItemStack>> FUNCTION_0D = IRecipeComponent.simple(
                CraftTweakerConstants.rl("processing/function_0d"),
                new TypeToken<>() {},
                Object::equals // TODO("")
        );
        
        // TODO("")
        public static final IRecipeComponent<BiFunction<IItemStack, IItemStack[], IItemStack>> FUNCTION_1D = IRecipeComponent.simple(
                CraftTweakerConstants.rl("processing/function_1d"),
                new TypeToken<>() {},
                Object::equals // TODO("")
        );
        
        // TODO("")
        public static final IRecipeComponent<BiFunction<IItemStack, IItemStack[][], IItemStack>> FUNCTION_2D = IRecipeComponent.simple(
                CraftTweakerConstants.rl("processing/function_2d"),
                new TypeToken<>() {},
                Object::equals // TODO("")
        );
        
        public static final IRecipeComponent<Number> TIME = IRecipeComponent.simple(
                CraftTweakerConstants.rl("processing/time"),
                new TypeToken<>() {},
                RecipeComponentEqualityCheckers::areNumbersEqual
        );
        
        private Processing() {}
        
    }
    
    /**
     * Holds {@link IRecipeComponent}s representing data used to determine the output of recipes.
     *
     * <p>Experience production from the furnace or similar machines is also considered an output, as it is awarded only
     * when the recipe has reached completion.</p>
     *
     * @since 10.0.0
     */
    public static final class Output {
        
        public static final IRecipeComponent<Number> EXPERIENCE = IRecipeComponent.simple(
                CraftTweakerConstants.rl("output/experience"),
                new TypeToken<>() {},
                RecipeComponentEqualityCheckers::areNumbersEqual
        );
        
        public static final IRecipeComponent<IItemStack> ITEMS = IRecipeComponent.simple(
                CraftTweakerConstants.rl("output/items"),
                new TypeToken<>() {},
                RecipeComponentEqualityCheckers::areStacksEqual
        );
        
        private Output() {}
        
    }
    
    private BuiltinRecipeComponents() {}
    
}
