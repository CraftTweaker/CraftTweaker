package com.blamejared.crafttweaker.impl.actions.items.tooltips;

import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.tooltip.ITooltipFunction;

public class ActionModifyTooltip extends ActionTooltipBase {
    
    private final ITooltipFunction function;
    
    public ActionModifyTooltip(IIngredient stack, ITooltipFunction function) {
        
        super(stack);
        this.function = function;
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
        
        return "Adding advanced tooltip to: " + stack.getCommandString();
    }
    
    
    @Override
    public String describeUndo() {
        
        return "Undoing addition of advanced tooltip to: " + stack.getCommandString();
    }
    
}
