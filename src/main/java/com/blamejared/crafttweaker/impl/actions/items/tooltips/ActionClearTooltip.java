package com.blamejared.crafttweaker.impl.actions.items.tooltips;

import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.tooltip.ITooltipFunction;

public class ActionClearTooltip extends ActionTooltipBase {
    
    
    private final ITooltipFunction function;
    
    public ActionClearTooltip(IIngredient stack) {
        
        super(stack);
        this.function = (stack1, tooltip, isAdvanced) -> {
            tooltip.clear();
        };
    }
    
    @Override
    public void apply() {
        
        getTooltip().add(function);
    }
    
    @Override
    public void undo() {
        
        getTooltip().remove(function);
    }
    
    @Override
    public String describe() {
        
        return "Clearing the tooltip for: " + stack.getCommandString();
    }
    
    @Override
    public String describeUndo() {
        
        return "Undoing clearing the tooltip for: " + stack.getCommandString();
    }
    
}
