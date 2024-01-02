package com.blamejared.crafttweaker.natives.event.tick;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.NeoForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.neoforged.neoforge.event.entity.living.LivingEvent;

@ZenRegister
@Document("neoforge/api/event/tick/LivingTickEvent")
@NativeTypeRegistration(value = LivingEvent.LivingTickEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.tick.LivingTickEvent")
public class ExpandLivingTickEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<LivingEvent.LivingTickEvent> BUS = IEventBus.cancelable(
            LivingEvent.LivingTickEvent.class,
            NeoForgeEventBusWire.of(),
            NeoForgeEventCancellationCarrier.of()
    );
    
}
