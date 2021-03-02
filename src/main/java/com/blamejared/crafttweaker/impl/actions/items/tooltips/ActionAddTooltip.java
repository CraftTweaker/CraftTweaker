package com.blamejared.crafttweaker.impl.actions.items.tooltips;

import com.blamejared.crafttweaker.api.actions.IRuntimeAction;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.events.CTClientEventHandler;
import com.blamejared.crafttweaker.impl.util.text.MCTextComponent;
import net.minecraftforge.fml.LogicalSide;

import java.util.LinkedList;

public class ActionAddTooltip implements IRuntimeAction {
    
    private final IIngredient stack;
    private final MCTextComponent content;
    
    public ActionAddTooltip(IIngredient stack, MCTextComponent content) {
        this.stack = stack;
        this.content = content;
    }
    
    @Override
    public void apply() {
        
        CTClientEventHandler.TOOLTIPS.computeIfAbsent(stack, iItemStack -> new LinkedList<>()).add((stack1, tooltip, isAdvanced) -> tooltip.add(content));
    }
    
    @Override
    public String describe() {
        return "Adding \"" + content + "\" to the tooltip for: " + stack.getCommandString();
    }
    
    @Override
    public boolean shouldApplyOn(LogicalSide side) {
        return side.isClient();
    }
}
