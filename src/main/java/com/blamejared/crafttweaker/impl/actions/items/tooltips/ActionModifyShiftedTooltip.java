package com.blamejared.crafttweaker.impl.actions.items.tooltips;

import com.blamejared.crafttweaker.api.actions.IRuntimeAction;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.tooltip.ITooltipFunction;
import com.blamejared.crafttweaker.impl.events.CTClientEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.fml.LogicalSide;

import java.util.LinkedList;

public class ActionModifyShiftedTooltip implements IRuntimeAction {
    
    private final IIngredient stack;
    private final ITooltipFunction shiftedFunction;
    private final ITooltipFunction unshiftedFunction;
    
    
    public ActionModifyShiftedTooltip(IIngredient stack, ITooltipFunction shiftedFunction, ITooltipFunction unshiftedFunction) {
        
        this.stack = stack;
        this.shiftedFunction = shiftedFunction;
        this.unshiftedFunction = unshiftedFunction;
    }
    
    @Override
    public void apply() {
        
        CTClientEventHandler.TOOLTIPS.computeIfAbsent(stack, iItemStack -> new LinkedList<>())
                .add((stack1, tooltip, isAdvanced) -> {
                    
                    final KeyBinding keyBindSneak = Minecraft.getInstance().gameSettings.keyBindSneak;
                    
                    if(InputMappings.isKeyDown(Minecraft.getInstance()
                            .getMainWindow()
                            .getHandle(), keyBindSneak.getKey().getKeyCode())) {
                        shiftedFunction.apply(stack1, tooltip, isAdvanced);
                    } else {
                        if(unshiftedFunction != null) {
                            unshiftedFunction.apply(stack1, tooltip, isAdvanced);
                        }
                    }
                    
                    
                });
    }
    
    @Override
    public String describe() {
        
        return "Adding advanced shifted tooltip to: " + stack.getCommandString();
    }
    
    @Override
    public boolean shouldApplyOn(LogicalSide side) {
        
        return side.isClient();
    }
    
}
