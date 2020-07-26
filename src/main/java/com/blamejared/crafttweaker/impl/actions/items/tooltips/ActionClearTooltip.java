package com.blamejared.crafttweaker.impl.actions.items.tooltips;

import com.blamejared.crafttweaker.api.actions.IRuntimeAction;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.events.CTEventHandler;

import java.util.LinkedList;

public class ActionClearTooltip implements IRuntimeAction {
    
    private final IItemStack stack;
    
    public ActionClearTooltip(IItemStack stack) {
        this.stack = stack;
    }
    
    @Override
    public void apply() {
        
        CTEventHandler.TOOLTIPS.computeIfAbsent(stack, iItemStack -> new LinkedList<>()).add((stack1, tooltip, isAdvanced) -> {
            tooltip.clear();
        });
    }
    
    @Override
    public String describe() {
        return "Clearing the tooltip for: " + stack.getCommandString();
    }
    
}
