package com.blamejared.crafttweaker.impl.actions.items.tooltips;

import com.blamejared.crafttweaker.api.actions.IRuntimeAction;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.events.CTClientEventHandler;
import com.blamejared.crafttweaker.impl.util.text.MCTextComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.fml.LogicalSide;

import java.util.LinkedList;

public class ActionAddShiftedTooltip implements IRuntimeAction {
    
    private final IItemStack stack;
    private final MCTextComponent content;
    private final MCTextComponent showMessage;
    
    
    public ActionAddShiftedTooltip(IItemStack stack, MCTextComponent content, MCTextComponent showMessage) {
        this.stack = stack;
        this.content = content;
        this.showMessage = showMessage;
    }
    
    @Override
    public void apply() {
    
        CTClientEventHandler.TOOLTIPS.computeIfAbsent(stack, iItemStack -> new LinkedList<>()).add((stack1, tooltip, isAdvanced) -> {
            
            final KeyBinding keyBindSneak = Minecraft.getInstance().gameSettings.keyBindSneak;
            
            if(InputMappings.isKeyDown(Minecraft.getInstance().mainWindow.getHandle(), keyBindSneak.getKey().getKeyCode())) {
                tooltip.add(content);
            } else {
                if(showMessage != null && !showMessage.getString().isEmpty()) {
                    tooltip.add(showMessage);
                }
            }
            
            
        });
    }
    
    @Override
    public String describe() {
        return "Adding \"" + content + "\" to the shift tooltip for: " + stack.getCommandString();
    }
    
    @Override
    public boolean shouldApplyOn(LogicalSide side) {
        return side.isClient();
    }
}
