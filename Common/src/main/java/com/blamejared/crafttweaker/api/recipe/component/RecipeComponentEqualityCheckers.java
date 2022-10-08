package com.blamejared.crafttweaker.api.recipe.component;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;

import java.util.Objects;

/**
 * Holds equality checkers for the various {@linkplain BuiltinRecipeComponents builtin components}.
 *
 * <p>These checkers are provided separately in case creators of new {@link IRecipeComponent}s want to reuse them.</p>
 *
 * @since 10.0.0
 */
public final class RecipeComponentEqualityCheckers {
    
    public static boolean areIngredientsEqual(final IIngredient a, final IIngredient b) {
        
        return Objects.equals(a, b) || (a.contains(b) && b.contains(a));
    }
    
    public static boolean areStacksEqual(final IItemStack a, final IItemStack b) {
        
        return a.matches(b, false);
    }
    
    public static <T extends Number> boolean areNumbersEqual(final T a, final T b) {
        
        return a.doubleValue() == b.doubleValue();
    }
    
    public static <T> boolean notComparable(final T a, final T b) {
        
        throw new UnsupportedOperationException("Cannot compare " + a + " and " + b);
    }
    
}
