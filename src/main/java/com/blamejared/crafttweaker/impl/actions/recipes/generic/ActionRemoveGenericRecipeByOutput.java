package com.blamejared.crafttweaker.impl.actions.recipes.generic;

import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import net.minecraft.item.crafting.IRecipe;

public class ActionRemoveGenericRecipeByOutput extends ActionRemoveGenericRecipeBase {
    
    private final IIngredient output;
    
    public ActionRemoveGenericRecipeByOutput(IIngredient output) {
        
        this.output = output;
    }
    
    @Override
    public String describe() {
        
        return "Removing all recipes that output " + output.getCommandString();
    }
    
    @Override
    protected boolean shouldRemove(IRecipe<?> recipe) {
        
        final MCItemStackMutable recipeOutput = new MCItemStackMutable(recipe.getRecipeOutput());
        return output.matches(recipeOutput);
    }
    
}
