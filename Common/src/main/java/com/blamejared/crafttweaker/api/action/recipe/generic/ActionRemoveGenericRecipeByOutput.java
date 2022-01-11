package com.blamejared.crafttweaker.api.action.recipe.generic;


import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.platform.Services;
import net.minecraft.world.item.crafting.Recipe;

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
    protected boolean shouldRemove(Recipe<?> recipe) {
        
        final IItemStack recipeOutput = Services.PLATFORM.createMCItemStack(recipe.getResultItem());
        return output.matches(recipeOutput);
    }
    
}
