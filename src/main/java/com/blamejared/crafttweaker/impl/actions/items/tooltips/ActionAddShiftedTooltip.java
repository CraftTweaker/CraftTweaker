package com.blamejared.crafttweaker.impl.actions.items.tooltips;

import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.tooltip.ITooltipFunction;
import com.blamejared.crafttweaker.api.util.ClientHelper;
import com.blamejared.crafttweaker.impl.util.text.MCTextComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

public class ActionAddShiftedTooltip extends ActionTooltipBase {
    
    private final MCTextComponent content;
    private final ITooltipFunction function;
    
    public ActionAddShiftedTooltip(IIngredient stack, MCTextComponent content, MCTextComponent showMessage) {
        
        super(stack);
        this.content = content;
        this.function = (stack1, tooltip, isAdvanced) -> {
            
            final KeyBinding keyBindSneak = Minecraft.getInstance().gameSettings.keyBindSneak;
            
            if(ClientHelper.getIsKeyPressed(keyBindSneak.getKeyBinding())) {
                tooltip.add(content);
            } else {
                if(showMessage != null && !showMessage.getString().isEmpty()) {
                    tooltip.add(showMessage);
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
        
        return "Adding \"" + content.asString() + "\" to the shift tooltip for: " + stack.getCommandString();
    }
    
    @Override
    public String describeUndo() {
        
        return "Undoing addition of \"" + content.asString() + "\" to the shift tooltip for: " + stack.getCommandString();
    }
    
}
