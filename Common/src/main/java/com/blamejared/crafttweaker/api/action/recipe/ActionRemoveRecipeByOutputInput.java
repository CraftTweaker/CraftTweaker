package com.blamejared.crafttweaker.api.action.recipe;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.impl.helper.AccessibleElementsProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import org.apache.logging.log4j.Logger;

public class ActionRemoveRecipeByOutputInput<T extends Recipe<?>> extends ActionRemoveRecipe<T> {
    
    private final IIngredient output;
    private final IIngredient input;
    
    public ActionRemoveRecipeByOutputInput(IRecipeManager<T> manager, IIngredient output, IIngredient input) {
        
        super(manager, recipe -> {
            ItemStack recipeOutput = AccessibleElementsProvider.get().registryAccess(recipe::getResultItem);
            if(output.matches(IItemStack.ofMutable(recipeOutput))) {
                for(IItemStack item : input.getItems()) {
                    if(recipe.getIngredients().get(0).test(item.getInternal())) {
                        return true;
                    }
                }
            }
            return false;
        }, action -> "Removing \"" + action.getRecipeTypeName() + "\" recipes that output: " + output + "\" from an input of: " + input.getCommandString());
        this.output = output;
        this.input = input;
        
    }
    
    @Override
    public boolean validate(Logger logger) {
        
        if(output == null) {
            logger.warn("output cannot be null!", new IllegalArgumentException("output IItemStack cannot be null!"));
            return false;
        }
        if(input == null) {
            logger.warn("input cannot be null!", new IllegalArgumentException("input IIngredient cannot be null!"));
            return false;
        }
        return true;
    }
    
}
