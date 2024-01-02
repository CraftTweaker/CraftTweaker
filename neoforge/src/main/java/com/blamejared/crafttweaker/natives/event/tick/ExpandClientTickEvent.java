package com.blamejared.crafttweaker.natives.event.tick;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.neoforged.neoforge.event.TickEvent;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/tick/ClientTickEvent")
@NativeTypeRegistration(value = TickEvent.ClientTickEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.tick.ClientTickEvent")
public class ExpandClientTickEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<TickEvent.ClientTickEvent> BUS = IEventBus.direct(
            TickEvent.ClientTickEvent.class,
            NeoForgeEventBusWire.of()
    );
    
}
