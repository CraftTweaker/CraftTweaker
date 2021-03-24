package com.blamejared.crafttweaker.impl.actions.recipes;

import com.blamejared.crafttweaker.api.exceptions.ScriptException;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.logger.ILogger;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import net.minecraft.item.ItemStack;

public class ActionRemoveRecipeByOutputInput extends ActionRemoveRecipe {
    
    private final IItemStack output;
    private final IIngredient input;
    
    public ActionRemoveRecipeByOutputInput(IRecipeManager manager, IItemStack output, IIngredient input) {
        
        super(manager, recipe -> {
            ItemStack recipeOutput = recipe.getRecipeOutput();
            if(output.matches(new MCItemStackMutable(recipeOutput))) {
                for(IItemStack item : input.getItems()) {
                    if(recipe.getIngredients().get(0).test(item.getInternal())) {
                        return true;
                    }
                }
            }
            return false;
        }, action -> "Removing \"" + action.getRecipeTypeName() + "\" recipes that output: " + output + "\" from an input of: " + input);
        this.output = output;
        this.input = input;
        
    }
    
    @Override
    public boolean validate(ILogger logger) {
        
        if(output == null) {
            logger.throwingWarn("output cannot be null!", new ScriptException("output IItemStack cannot be null!"));
            return false;
        }
        if(input == null) {
            logger.throwingWarn("input cannot be null!", new ScriptException("input IIngredient cannot be null!"));
            return false;
        }
        return true;
    }
    
}
