package com.blamejared.crafttweaker.impl.actions.items.tooltips;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.actions.IUndoableAction;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.tooltip.ITooltipFunction;
import com.blamejared.crafttweaker.impl.events.CTClientEventHandler;
import net.minecraftforge.fml.LogicalSide;

import java.util.LinkedList;

public abstract class ActionTooltipBase implements IUndoableAction {
    
    protected final IIngredient stack;
    
    public ActionTooltipBase(IIngredient stack) {
        
        this.stack = stack;
    }
    
    public LinkedList<ITooltipFunction> getTooltip() {
        
        return CTClientEventHandler.TOOLTIPS.computeIfAbsent(stack, iItemStack -> new LinkedList<>());
    }
    
    @Override
    public boolean shouldApplyOn(LogicalSide side) {
        
        return !CraftTweakerAPI.isServer();
    }
    
}
