package com.blamejared.crafttweaker.impl.actions.recipes;

import com.blamejared.crafttweaker.api.managers.IRecipeManager;

public class ActionRemoveAll extends ActionRecipeBase {
    
    
    public ActionRemoveAll(IRecipeManager manager) {
        
        super(manager);
    }
    
    @Override
    public void apply() {
        
        getRecipes().clear();
    }
    
    @Override
    public String describe() {
        
        return "Removing all \"" + getRecipeTypeName() + "\" recipes";
    }
    
}
