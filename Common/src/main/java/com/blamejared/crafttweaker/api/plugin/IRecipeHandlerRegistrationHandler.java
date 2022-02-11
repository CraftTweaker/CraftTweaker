package com.blamejared.crafttweaker.api.plugin;

import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandler;
import net.minecraft.world.item.crafting.Recipe;

public interface IRecipeHandlerRegistrationHandler {
    
    <T extends Recipe<?>> void registerRecipeHandler(final Class<? extends T> recipe, final IRecipeHandler<T> handler);
    
}
