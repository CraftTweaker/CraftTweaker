package com.blamejared.crafttweaker.api.events.recipes;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.recipes.IRecipeWriter;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.eventbus.api.Event;

/**
 * Alows mods to register {@link IRecipeWriter}s that will be used
 * when {@code /ct recipes} or {@code /ct recipes hand} is ran.
 */
public class RegisterRecipeWritersEvent extends Event {
    
    /**
     * Registers a new {@link IRecipeWriter} for a specific {@link IRecipe} class.
     *
     * @param recipeClass The Class that this IRecipeWriter should run for
     * @param writer      IRecipeWriter for the given IRecipe class.
     */
    public void register(Class<? extends IRecipe<?>> recipeClass, IRecipeWriter writer) {
        
        CraftTweakerAPI.getRecipeWriters().put(recipeClass, writer);
    }
    
}
