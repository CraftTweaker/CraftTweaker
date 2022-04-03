package com.blamejared.crafttweaker.api.recipe.handler;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import net.minecraft.world.item.crafting.Recipe;

public interface IRecipeHandlerRegistry {
    
    static <T extends Recipe<?>> IRecipeHandler<T> getHandlerFor(final T recipe) {
        
        return CraftTweakerAPI.getRegistry().getRecipeHandlerFor(recipe);
    }
    
    <T extends Recipe<?>> IRecipeHandler<T> getRecipeHandlerFor(final T recipe);
    
}
