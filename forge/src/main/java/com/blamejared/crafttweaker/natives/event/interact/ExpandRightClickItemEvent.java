package com.blamejared.crafttweaker.natives.event.interact;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

/**
 * The rightClickItem event is fired whenever the player right clicks with an item in their hand.
 * It does not offer any special getters, but you can still access all members from {@link PlayerInteractEvent}
 *
 * @docEvent canceled Item#onItemRightClick will not be called
 */
@ZenRegister
@ZenEvent
@Document("forge/api/event/interact/RightClickItemEvent")
@NativeTypeRegistration(value = PlayerInteractEvent.RightClickItem.class, zenCodeName = "crafttweaker.forge.api.player.interact.RightClickItemEvent")
public class ExpandRightClickItemEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<PlayerInteractEvent.RightClickItem> BUS = IEventBus.cancelable(
            PlayerInteractEvent.RightClickItem.class,
            ForgeEventBusWire.of(),
            ForgeEventCancellationCarrier.of()
    );
    
}
