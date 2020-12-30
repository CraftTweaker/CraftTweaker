package com.blamejared.crafttweaker.impl.actions.recipes.whole_registry;

import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import net.minecraft.item.crafting.IRecipe;

public class ActionRemoveFromWholeRegistryByOutput extends AbstractActionRemoveFromWholeRegistry {
    
    private final IItemStack output;
    
    public ActionRemoveFromWholeRegistryByOutput(IItemStack output) {
        this.output = output;
    }
    
    @Override
    public String describe() {
        return "Removing all recipes that return " + output.getCommandString();
    }
    
    @Override
    protected boolean shouldRemove(IRecipe<?> recipe) {
        final MCItemStackMutable recipeOutput = new MCItemStackMutable(recipe.getRecipeOutput());
        return output.matches(recipeOutput);
    }
}
