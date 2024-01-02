package com.blamejared.crafttweaker.natives.event.interact;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.NeoForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

/**
 * The rightClickItem event is fired whenever the player right clicks with an item in their hand.
 * It does not offer any special getters, but you can still access all members from {@link PlayerInteractEvent}
 *
 * @docEvent canceled Item#onItemRightClick will not be called
 */
@ZenRegister
@ZenEvent
@Document("neoforge/api/event/interact/RightClickItemEvent")
@NativeTypeRegistration(value = PlayerInteractEvent.RightClickItem.class, zenCodeName = "crafttweaker.neoforge.api.player.interact.RightClickItemEvent")
public class ExpandRightClickItemEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<PlayerInteractEvent.RightClickItem> BUS = IEventBus.cancelable(
            PlayerInteractEvent.RightClickItem.class,
            NeoForgeEventBusWire.of(),
            NeoForgeEventCancellationCarrier.of()
    );
    
}
