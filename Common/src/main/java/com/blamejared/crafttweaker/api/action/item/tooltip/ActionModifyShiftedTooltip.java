package com.blamejared.crafttweaker.api.action.item.tooltip;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.tooltip.ITooltipFunction;
import com.blamejared.crafttweaker.platform.Services;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;

public class ActionModifyShiftedTooltip extends ActionTooltipBase {
    
    private final ITooltipFunction function;
    
    public ActionModifyShiftedTooltip(IIngredient stack, ITooltipFunction shiftedFunction, ITooltipFunction unshiftedFunction) {
        
        super(stack);
        this.function = (stack1, tooltip, context) -> {
            
            final KeyMapping keyBindSneak = Minecraft.getInstance().options.keyShift;
            
            if(Services.CLIENT.isKeyDownExtra(keyBindSneak)) {
                shiftedFunction.apply(stack1, tooltip, context);
            } else {
                if(unshiftedFunction != null) {
                    unshiftedFunction.apply(stack1, tooltip, context);
                }
            }
            
            
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
        
        return "Adding advanced shifted tooltip to: " + stack.getCommandString();
    }
    
    @Override
    public String describeUndo() {
        
        return "Undoing addition of advanced shifted tooltip to: " + stack.getCommandString();
    }
    
}
