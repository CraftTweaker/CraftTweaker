package com.blamejared.crafttweaker.impl.actions.recipes;

import com.blamejared.crafttweaker.api.actions.IRuntimeAction;
import com.blamejared.crafttweaker.api.managers.IRecipeManager;

public abstract class ActionRecipeBase implements IRuntimeAction {
    
    private final IRecipeManager manager;
    
    public ActionRecipeBase(IRecipeManager manager) {
        this.manager = manager;
    }
    
    public IRecipeManager getManager() {
        return manager;
    }
}
