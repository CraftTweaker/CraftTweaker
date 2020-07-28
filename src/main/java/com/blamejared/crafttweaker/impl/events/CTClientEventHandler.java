package com.blamejared.crafttweaker.impl.events;

import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.item.tooltip.ITooltipFunction;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import com.blamejared.crafttweaker.impl.util.text.MCTextComponent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CTClientEventHandler {
    
    public static final Map<IItemStack, LinkedList<ITooltipFunction>> TOOLTIPS = new HashMap<>();
    
    @SubscribeEvent
    public void handleTooltips(ItemTooltipEvent e) {
        for(IItemStack stack : TOOLTIPS.keySet()) {
            if(stack.matches(new MCItemStackMutable(e.getItemStack()))) {
                List<MCTextComponent> collect = e.getToolTip().stream().map(MCTextComponent::new).collect(Collectors.toList());
                for(ITooltipFunction function : TOOLTIPS.get(stack)) {
                    function.apply(new MCItemStackMutable(e.getItemStack()), collect, e.getFlags().isAdvanced());
                }
                e.getToolTip().clear();
                e.getToolTip().addAll(collect.stream().map(MCTextComponent::getInternal).collect(Collectors.toList()));
            }
        }
    }
    
}
