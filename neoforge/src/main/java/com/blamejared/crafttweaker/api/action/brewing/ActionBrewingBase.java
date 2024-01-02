package com.blamejared.crafttweaker.api.action.brewing;

import com.blamejared.crafttweaker.api.action.base.IUndoableAction;
import com.blamejared.crafttweaker.api.action.internal.CraftTweakerAction;
import com.blamejared.crafttweaker.api.zencode.IScriptLoadSource;
import net.neoforged.neoforge.common.brewing.IBrewingRecipe;
import org.apache.logging.log4j.Logger;

import java.util.List;

public abstract class ActionBrewingBase extends CraftTweakerAction implements IUndoableAction {
    
    protected final List<IBrewingRecipe> recipes;
    
    protected ActionBrewingBase(List<IBrewingRecipe> recipes) {
        
        this.recipes = recipes;
    }
    
    @Override
    public boolean shouldApplyOn(final IScriptLoadSource source, final Logger logger) {
        
        return true;
    }
    
}
