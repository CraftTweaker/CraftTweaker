package com.blamejared.crafttweaker.impl.actions.recipes;

import com.blamejared.crafttweaker.api.exceptions.ScriptException;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.logger.ILogger;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;

public class ActionRemoveRecipeByOutput extends ActionRemoveRecipe {
    
    private final IIngredient output;
    
    public ActionRemoveRecipeByOutput(IRecipeManager manager, IIngredient output) {
        
        super(manager,
                recipe -> output.matches(new MCItemStackMutable(recipe.getRecipeOutput())));
        this.output = output;
        describeDefaultRemoval(output);
    }
    
    @Override
    public boolean validate(ILogger logger) {
        
        if(output == null) {
            logger.throwingWarn("output cannot be null!", new ScriptException("output IItemStack cannot be null!"));
            return false;
        }
        return true;
    }
    
}
