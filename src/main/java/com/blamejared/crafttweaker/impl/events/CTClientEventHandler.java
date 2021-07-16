package com.blamejared.crafttweaker.impl.events;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.item.IIngredient;
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
    
    public static final Map<IIngredient, LinkedList<ITooltipFunction>> TOOLTIPS = new HashMap<>();
    
    @SubscribeEvent
    public void handleTooltips(ItemTooltipEvent e) {
        for(IIngredient ingredient : TOOLTIPS.keySet()) {
            if(ingredient.matches(new MCItemStackMutable(e.getItemStack()))) {
                List<MCTextComponent> collect = e.getToolTip().stream().map(MCTextComponent::new).collect(Collectors.toList());
                for(ITooltipFunction function : TOOLTIPS.get(ingredient)) {
                    try {
                        function.apply(new MCItemStackMutable(e.getItemStack()), collect, e.getFlags().isAdvanced());
                    } catch (final Exception exception) {
                        CraftTweakerAPI.logger.throwingErr(
                                String.format(
                                        "Unable to run one of the tooltip functions for %s on %s due to an error (for experts, refer to %s)",
                                        ingredient.getCommandString(),
                                        new MCItemStackMutable(e.getItemStack()).getCommandString(),
                                        function.getClass().getName()
                                ),
                                exception
                        );
                    }
                }
                e.getToolTip().clear();
                e.getToolTip().addAll(collect.stream().map(MCTextComponent::getInternal).collect(Collectors.toList()));
            }
        }
    }
    
}
