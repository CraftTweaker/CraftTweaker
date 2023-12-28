package com.blamejared.crafttweaker.api.action.recipe.generic;


import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.helper.AccessibleElementsProvider;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;

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
    protected boolean shouldRemove(RecipeHolder<?> holder) {
        
        return output.matches(IItemStack.of(AccessibleElementsProvider.get().registryAccess(holder.value()::getResultItem)));
    }
    
}
