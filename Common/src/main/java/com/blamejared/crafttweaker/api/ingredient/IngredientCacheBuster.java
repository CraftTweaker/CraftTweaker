package com.blamejared.crafttweaker.api.ingredient;

import com.blamejared.crafttweaker.platform.Services;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

/**
 * Used to bust the {@link Ingredient}#itemStacks cache when the {@link Ingredient} was resolved during a Server reload (When an instance of {@link net.minecraft.tags.TagManager} is available.
 */
public class IngredientCacheBuster {
    
    private static boolean claimed;
    private static final List<Ingredient> ingredients = new ArrayList<>();
    
    /**
     * Starts caching ingredients that are dissolved.
     */
    public static void claim() {
        
        claimed = true;
    }
    
    /**
     * Stops caching dissolved {@link Ingredient}s and invalidates the {@link Ingredient}s that were dissolved while claimed.
     */
    public static void release() {
        
        claimed = false;
        Services.PLATFORM.invalidateIngredients(ingredients);
    }
    
    /**
     * Returns true if the cache buster is running.
     *
     * @return true if the cache buster is running, false otherwise.
     */
    public static boolean claimed() {
        
        return claimed;
    }
    
    /**
     * Stores an {@link Ingredient} being dissolved to be invalidated at a later point.
     *
     * @param ingredient The ingredient to invalidate at a later point.
     */
    public static void store(Ingredient ingredient) {
        
        if(ingredient == null) {
            throw new IllegalStateException("Cannot store a null ingredient!");
        }
        ingredients.add(ingredient);
    }
    
}
