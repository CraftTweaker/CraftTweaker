package com.blamejared.crafttweaker.api.recipe.component;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.type.IIngredientEmpty;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.MirrorAxis;
import com.blamejared.crafttweaker.api.recipe.fun.RecipeFunction0D;
import com.blamejared.crafttweaker.api.recipe.fun.RecipeFunction1D;
import com.blamejared.crafttweaker.api.recipe.fun.RecipeFunction2D;
import com.blamejared.crafttweaker.api.util.random.Percentaged;
import com.google.gson.reflect.TypeToken;
import com.mojang.datafixers.util.Pair;
import net.minecraft.Util;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.CraftingBookCategory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
    
        public static final IRecipeComponent<CookingBookCategory> COOKING_BOOK_CATEGORY = IRecipeComponent.simple(
                CraftTweakerConstants.rl("metadata/cooking_book_category"),
                new TypeToken<>() {},
                Object::equals
        );
    
        public static final IRecipeComponent<CraftingBookCategory> CRAFTING_BOOK_CATEGORY = IRecipeComponent.simple(
                CraftTweakerConstants.rl("metadata/crafting_book_category"),
                new TypeToken<>() {},
                Object::equals
        );
        
        public static final IRecipeComponent<MirrorAxis> MIRROR_AXIS = IRecipeComponent.simple(
                CraftTweakerConstants.rl("metadata/mirror_axis"),
                new TypeToken<>() {},
                Object::equals
        );
        
        public static final IRecipeComponent<Pair<Integer, Integer>> SHAPE_SIZE_2D = IRecipeComponent.simple(
                CraftTweakerConstants.rl("metadata/shape_size_2d"),
                new TypeToken<>() {},
                RecipeComponentEqualityCheckers::notComparable
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
        
        public static final IRecipeComponent<RecipeFunction0D> FUNCTION_0D = IRecipeComponent.simple(
                CraftTweakerConstants.rl("processing/function_0d"),
                new TypeToken<>() {},
                RecipeComponentEqualityCheckers::notComparable
        );
        
        public static final IRecipeComponent<RecipeFunction1D> FUNCTION_1D = IRecipeComponent.simple(
                CraftTweakerConstants.rl("processing/function_1d"),
                new TypeToken<>() {},
                RecipeComponentEqualityCheckers::notComparable
        );
        
        public static final IRecipeComponent<RecipeFunction2D> FUNCTION_2D = IRecipeComponent.simple(
                CraftTweakerConstants.rl("processing/function_2d"),
                new TypeToken<>() {},
                RecipeComponentEqualityCheckers::notComparable
        );
        
        public static final IRecipeComponent<Integer> TIME = IRecipeComponent.simple(
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
    
        public static final IRecipeComponent<Percentaged<IItemStack>> CHANCED_ITEMS = IRecipeComponent.simple(
                CraftTweakerConstants.rl("output/chanced_items"),
                new TypeToken<>() {},
                RecipeComponentEqualityCheckers::areStacksEqual
        );
        
        public static final IRecipeComponent<Float> EXPERIENCE = IRecipeComponent.simple(
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
