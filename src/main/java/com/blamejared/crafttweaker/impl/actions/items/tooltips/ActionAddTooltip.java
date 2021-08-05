package com.blamejared.crafttweaker.impl.actions.items.tooltips;

import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.tooltip.ITooltipFunction;
import com.blamejared.crafttweaker.impl.util.text.MCTextComponent;

public class ActionAddTooltip extends ActionTooltipBase {
    
    private final MCTextComponent content;
    private final ITooltipFunction function;
    
    public ActionAddTooltip(IIngredient stack, MCTextComponent content) {
        
        super(stack);
        this.content = content;
        this.function = (stack1, tooltip, isAdvanced) -> {
            tooltip.add(content);
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
        
        return "Adding \"" + content.asString() + "\" to the tooltip for: " + stack.getCommandString();
    }
    
    @Override
    public String describeUndo() {
        
        return "Undoing addition of \"" + content.asString() + "\" to the tooltip for: " + stack.getCommandString();
    }
    
}
