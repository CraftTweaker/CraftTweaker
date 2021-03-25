package com.blamejared.crafttweaker.impl.actions.items.tooltips;

import com.blamejared.crafttweaker.api.actions.IRuntimeAction;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.events.CTClientEventHandler;
import net.minecraftforge.fml.LogicalSide;

import java.util.LinkedList;

public class ActionClearTooltip implements IRuntimeAction {
    
    private final IIngredient stack;
    
    public ActionClearTooltip(IIngredient stack) {
        this.stack = stack;
    }
    
    @Override
    public void apply() {
        
        CTClientEventHandler.TOOLTIPS.computeIfAbsent(stack, iItemStack -> new LinkedList<>()).add((stack1, tooltip, isAdvanced) -> {
            tooltip.clear();
        });
    }
    
    @Override
    public String describe() {
        return "Clearing the tooltip for: " + stack.getCommandString();
    }
    
    @Override
    public boolean shouldApplyOn(LogicalSide side) {
        return side.isClient();
    }
}
