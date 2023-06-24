package com.blamejared.crafttweaker.natives.event.item;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.CommonAdaptingEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;

/**
 * This event is fired whenever a tooltip is calculated.
 *
 * <p> Note: the player may not always be present, make sure you check if it is null before trying to use it</p>
 *
 * @docParam this event
 */
@ZenRegister
@ZenEvent
@Document("forge/api/event/item/ItemTooltipEvent")
@NativeTypeRegistration(value = ItemTooltipEvent.class, zenCodeName = "crafttweaker.forge.api.event.item.ItemTooltipEvent")
public class ExpandItemTooltipEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<ItemTooltipEvent> BUS = IEventBus.direct(
            ItemTooltipEvent.class,
            ForgeEventBusWire.of()
    );
    
    /**
     * Gets the extra tooltip flags, such as if advanced tooltips should be displayed.
     *
     * @return The tooltip flags
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("flags")
    public static TooltipFlag getFlags(ItemTooltipEvent internal) {
        
        return internal.getFlags();
    }
    
    /**
     * Gets the ItemStack that the tooltip is for.
     *
     * @return The ItemStack that the tooltip is for.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("itemStack")
    public static IItemStack getItemStack(ItemTooltipEvent internal) {
        
        return IItemStack.of(internal.getItemStack());
    }
    
    /**
     * Gets the list of {@link Component}s that make up the tooltip.
     *
     * @return The list of {@link Component} that make up the tooltip.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("tooltip")
    public static List<Component> getToolTip(ItemTooltipEvent internal) {
        
        return internal.getToolTip();
    }
    
    /**
     * Gets the player that is viewing the tooltip.
     *
     * <p>Note: The player can be null</p>
     *
     * @return The player that is viewing the tooltip.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("player")
    @ZenCodeType.Nullable
    public static Player getPlayer(ItemTooltipEvent internal) {
        
        return internal.getEntity();
    }
    
}