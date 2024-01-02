package com.blamejared.crafttweaker.natives.event.interact;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;


/**
 * The rightClickEmpty event is fired whenever the player right clicks with an empty hand.
 * It does not offer any special getters, but you can still access all members from {@link PlayerInteractEvent}
 */
@ZenRegister
@ZenEvent
@Document("neoforge/api/event/interact/RightClickEmptyEvent")
@NativeTypeRegistration(value = PlayerInteractEvent.RightClickEmpty.class, zenCodeName = "crafttweaker.neoforge.api.event.interact.RightClickEmptyEvent")
public class ExpandRightClickEmptyEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<PlayerInteractEvent.RightClickEmpty> BUS = IEventBus.direct(
            PlayerInteractEvent.RightClickEmpty.class,
            NeoForgeEventBusWire.of()
    );
    
}
