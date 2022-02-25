package com.blamejared.crafttweaker.api.action.brewing;

import com.blamejared.crafttweaker.api.action.base.IUndoableAction;
import net.minecraftforge.common.brewing.IBrewingRecipe;

import java.util.List;

public abstract class ActionBrewingBase implements IUndoableAction {
    
    protected final List<IBrewingRecipe> recipes;
    
    protected ActionBrewingBase(List<IBrewingRecipe> recipes) {
        
        this.recipes = recipes;
    }
    
    @Override
    public boolean shouldApplyOn(ScriptLoadingOptions.ScriptLoadSource source) {
        
        return true;
    }
    
}
