package com.blamejared.crafttweaker.api.action.item.tooltip;

import com.blamejared.crafttweaker.api.action.base.IUndoableAction;
import com.blamejared.crafttweaker.api.action.internal.CraftTweakerAction;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.tooltip.ITooltipFunction;
import com.blamejared.crafttweaker.api.zencode.IScriptLoadSource;
import com.blamejared.crafttweaker.platform.Services;

import java.util.LinkedList;

public abstract class ActionTooltipBase extends CraftTweakerAction implements IUndoableAction {
    
    protected final IIngredient stack;
    
    public ActionTooltipBase(IIngredient stack) {
        
        this.stack = stack;
    }
    
    public LinkedList<ITooltipFunction> getTooltip() {
        
        return Services.CLIENT.getTooltips().computeIfAbsent(stack, iItemStack -> new LinkedList<>());
    }
    
    @Override
    public boolean shouldApplyOn(final IScriptLoadSource source) {
        
        return Services.DISTRIBUTION.isClient();
    }
    
}
