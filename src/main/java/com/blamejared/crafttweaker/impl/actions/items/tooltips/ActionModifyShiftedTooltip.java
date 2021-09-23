package com.blamejared.crafttweaker.impl.actions.items.tooltips;

import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.tooltip.ITooltipFunction;
import com.blamejared.crafttweaker.api.util.ClientHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

public class ActionModifyShiftedTooltip extends ActionTooltipBase {
    
    private final ITooltipFunction shiftedFunction;
    private final ITooltipFunction unshiftedFunction;
    
    private final ITooltipFunction function;
    
    public ActionModifyShiftedTooltip(IIngredient stack, ITooltipFunction shiftedFunction, ITooltipFunction unshiftedFunction) {
        
        super(stack);
        this.shiftedFunction = shiftedFunction;
        this.unshiftedFunction = unshiftedFunction;
        this.function = (stack1, tooltip, isAdvanced) -> {
            
            final KeyBinding keyBindSneak = Minecraft.getInstance().gameSettings.keyBindSneak;
            
            if(ClientHelper.getIsKeyPressed(keyBindSneak.getKeyBinding())) {
                shiftedFunction.apply(stack1, tooltip, isAdvanced);
            } else {
                if(unshiftedFunction != null) {
                    unshiftedFunction.apply(stack1, tooltip, isAdvanced);
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
