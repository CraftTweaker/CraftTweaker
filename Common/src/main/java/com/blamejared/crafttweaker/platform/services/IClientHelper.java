package com.blamejared.crafttweaker.platform.services;

import com.blamejared.crafttweaker.api.entity.INameTagFunction;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.item.tooltip.ITooltipFunction;
import com.blamejared.crafttweaker.api.logging.CommonLoggers;
import com.blamejared.crafttweaker.platform.Services;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public interface IClientHelper {
    
    Map<IIngredient, LinkedList<ITooltipFunction>> TOOLTIPS = new HashMap<>();
    Map<Predicate<Entity>, INameTagFunction> NAMETAGS = new HashMap<>();
    
    default boolean isSingleplayer() {
        
        return Minecraft.getInstance().hasSingleplayerServer();
    }
    
    /**
     * Safely checks if a key is down.
     *
     * @param keyBinding The key to check
     *
     * @return True if the key is down. False otherwise.
     */
    boolean isKeyDown(KeyMapping keyBinding);
    
    /**
     * Safely checks if a key is down, including any modifiers.
     *
     * @param keyBinding The key to check
     *
     * @return True if the key is down. False otherwise.
     */
    boolean isKeyDownExtra(KeyMapping keyBinding);
    
    
    default Map<IIngredient, LinkedList<ITooltipFunction>> getTooltips() {
        
        return TOOLTIPS;
    }
    
    default void applyTooltips(ItemStack stack, TooltipFlag context, List<Component> lines) {
        
        IItemStack ctStack = IItemStack.of(stack);
        for(IIngredient ingredient : Services.CLIENT.getTooltips().keySet()) {
            if(!ingredient.matches(ctStack)) {
                continue;
            }
            Services.CLIENT.getTooltips().get(ingredient).forEach(function -> {
                try {
                    function.apply(ctStack, lines, context);
                } catch(final Exception exception) {
                    CommonLoggers.api().error(
                            "Unable to run one of the tooltip functions for {} on {} due to an error (for experts, refer to {})",
                            ingredient.getCommandString(),
                            ctStack.getCommandString(),
                            function.getClass().getName(),
                            exception
                    );
                }
            });
        }
    }
    
}
