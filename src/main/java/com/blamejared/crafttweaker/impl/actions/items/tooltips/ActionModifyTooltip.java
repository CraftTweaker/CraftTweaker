package com.blamejared.crafttweaker.impl.actions.items.tooltips;

import com.blamejared.crafttweaker.api.actions.IRuntimeAction;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.item.tooltip.ITooltipFunction;
import com.blamejared.crafttweaker.impl.events.CTClientEventHandler;
import net.minecraftforge.fml.LogicalSide;

import java.util.LinkedList;

public class ActionModifyTooltip implements IRuntimeAction {
    
    private final IIngredient stack;
    private final ITooltipFunction function;
    
    public ActionModifyTooltip(IIngredient stack, ITooltipFunction function) {
        this.stack = stack;
        this.function = function;
    }
    
    @Override
    public void apply() {
        
        CTClientEventHandler.TOOLTIPS.computeIfAbsent(stack, iItemStack -> new LinkedList<>()).add(function);
    }
    
    @Override
    public String describe() {
        return "Adding advanced tooltip to: " + stack.getCommandString();
    }
    
    @Override
    public boolean shouldApplyOn(LogicalSide side) {
        return side.isClient();
    }
}
