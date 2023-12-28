package com.blamejared.crafttweaker.api.action.item.tooltip;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.tooltip.ITooltipFunction;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ActionRemoveRegexTooltip extends ActionTooltipBase {
    
    private final Pattern regex;
    private final ITooltipFunction function;
    
    public ActionRemoveRegexTooltip(IIngredient stack, Pattern regex) {
        
        super(stack);
        this.regex = regex;
        this.function = (stack1, tooltip, context) -> {
            List<Component> content = new ArrayList<>();
            for(Component component : tooltip) {
                if(!regex.matcher(component.getString()).find()) {
                    content.add(component);
                }
            }
            tooltip.clear();
            tooltip.addAll(content);
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
        
        return "Removing from the tooltip for: " + stack.getCommandString() + " based on the regex: \"" + regex + "\"";
    }
    
    @Override
    public String describeUndo() {
        
        return "Undoing removal from the tooltip for: " + stack.getCommandString() + " based on the regex: \"" + regex + "\"";
    }
    
}
