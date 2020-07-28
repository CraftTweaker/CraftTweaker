package com.blamejared.crafttweaker.impl.actions.items.tooltips;

import com.blamejared.crafttweaker.api.actions.IRuntimeAction;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.events.CTClientEventHandler;
import com.blamejared.crafttweaker.impl.util.text.MCTextComponent;
import net.minecraftforge.fml.LogicalSide;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class ActionRemoveRegexTooltip implements IRuntimeAction {
    
    private final IItemStack stack;
    private final Pattern regex;
    
    public ActionRemoveRegexTooltip(IItemStack stack, Pattern regex) {
        this.stack = stack;
        this.regex = regex;
    }
    
    @Override
    public void apply() {
        
        CTClientEventHandler.TOOLTIPS.computeIfAbsent(stack, iItemStack -> new LinkedList<>()).add((stack1, tooltip, isAdvanced) -> {
            List<MCTextComponent> content = new ArrayList<>();
            for(MCTextComponent component : tooltip) {
                if(!regex.matcher(component.getFormattedText()).find()) {
                    content.add(component);
                }
            }
            tooltip.clear();
            tooltip.addAll(content);
        });
    }
    
    @Override
    public String describe() {
        return "Removing from the tooltip for: " + stack.getCommandString() + " based on the regex: \"" + regex + "\"";
    }
    
    @Override
    public boolean shouldApplyOn(LogicalSide side) {
        return side.isClient();
    }
}
