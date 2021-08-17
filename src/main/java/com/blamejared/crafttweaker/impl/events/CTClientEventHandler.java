package com.blamejared.crafttweaker.impl.events;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.entity.INameplateFunction;
import com.blamejared.crafttweaker.api.entity.NamePlateResult;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.tooltip.ITooltipFunction;
import com.blamejared.crafttweaker.impl.entity.MCEntityType;
import com.blamejared.crafttweaker.impl.item.MCItemStackMutable;
import com.blamejared.crafttweaker.impl.util.text.MCTextComponent;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.event.RenderNameplateEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CTClientEventHandler {
    
    public static final Map<IIngredient, LinkedList<ITooltipFunction>> TOOLTIPS = new HashMap<>();
    public static final Map<Predicate<Entity>, INameplateFunction> NAMEPLATES = new HashMap<>();
    
    
    @SubscribeEvent
    public void nameplate(RenderNameplateEvent e) {
        
        for(Predicate<Entity> predicate : NAMEPLATES.keySet()) {
            if(predicate.test(e.getEntity())) {
                try {
                    NamePlateResult namePlateResult = new NamePlateResult(e.getResult(), new MCTextComponent(e.getContent()), new MCTextComponent(e.getOriginalContent()));
                    NAMEPLATES.get(predicate).apply(e.getEntity(), namePlateResult);
                    e.setResult(namePlateResult.getResult());
                    e.setContent(namePlateResult.getContent().getInternal());
                } catch(final Exception exception) {
                    CraftTweakerAPI.logger.throwingErr(
                            String.format(
                                    "Unable to run one of the nameplate functions for %s due to an error (for experts, refer to %s)",
                                    new MCEntityType(e.getEntity().getType()).getCommandString(),
                                    NAMEPLATES.get(predicate).getClass().getName()
                            ),
                            exception
                    );
                }
            }
        }
    }
    
    @SubscribeEvent
    public void handleTooltips(ItemTooltipEvent e) {
        
        for(IIngredient ingredient : TOOLTIPS.keySet()) {
            if(ingredient.matches(new MCItemStackMutable(e.getItemStack()))) {
                List<MCTextComponent> collect = e.getToolTip()
                        .stream()
                        .map(MCTextComponent::new)
                        .collect(Collectors.toList());
                for(ITooltipFunction function : TOOLTIPS.get(ingredient)) {
                    try {
                        function.apply(new MCItemStackMutable(e.getItemStack()), collect, e.getFlags().isAdvanced());
                    } catch(final Exception exception) {
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
