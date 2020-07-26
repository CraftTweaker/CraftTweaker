package com.blamejared.crafttweaker.impl.actions.items.tooltips;

import com.blamejared.crafttweaker.api.actions.IRuntimeAction;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.item.tooltip.ITooltipFunction;
import com.blamejared.crafttweaker.impl.events.CTEventHandler;

import java.util.LinkedList;

public class ActionModifyTooltip implements IRuntimeAction {
    
    private final IItemStack stack;
    private final ITooltipFunction function;
    
    public ActionModifyTooltip(IItemStack stack, ITooltipFunction function) {
        this.stack = stack;
        this.function = function;
    }
    
    @Override
    public void apply() {
        
        CTEventHandler.TOOLTIPS.computeIfAbsent(stack, iItemStack -> new LinkedList<>()).add(function);
    }
    
    @Override
    public String describe() {
        return "Adding advanced tooltip to: " + stack.getCommandString();
    }
    
}
