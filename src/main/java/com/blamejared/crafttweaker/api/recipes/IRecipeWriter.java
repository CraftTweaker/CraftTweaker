package com.blamejared.crafttweaker.api.recipes;

import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import net.minecraft.item.crafting.IRecipe;


public interface IRecipeWriter {
    
    /**
     * Creates a String representation of a valid {@code addRecipe} (or alternative) call for the given {@link IRecipe}
     *
     * Recipe Dumps are triggered by the {@code /ct recipes} or {@code /ct recipes hand} commands.
     *
     * **NOTE** You do not and should not add a newline at the start or end, this is handled for you.
     *
     * @param builder StringBuilder used for the command output.
     * @param recipe  IRecipe that is being dumped.
     */
    void write(IRecipeManager manager, StringBuilder builder, IRecipe<?> recipe);
}
