package com.blamejared.crafttweaker.natives.event.entity.player;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/entity/player/PlayerLoggedInEvent")
@NativeTypeRegistration(value = PlayerEvent.PlayerLoggedInEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.entity.player.PlayerLoggedInEvent")
public class ExpandPlayerLoggedInEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<PlayerEvent.PlayerLoggedInEvent> BUS = IEventBus.direct(
            PlayerEvent.PlayerLoggedInEvent.class,
            NeoForgeEventBusWire.of()
    );
    
}
