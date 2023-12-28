package com.blamejared.crafttweaker.natives.event.tick;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.living.LivingEvent;

@ZenRegister
@Document("forge/api/event/tick/LivingTickEvent")
@NativeTypeRegistration(value = LivingEvent.LivingTickEvent.class, zenCodeName = "crafttweaker.forge.api.event.tick.LivingTickEvent")
public class ExpandLivingTickEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<LivingEvent.LivingTickEvent> BUS = IEventBus.cancelable(
            LivingEvent.LivingTickEvent.class,
            ForgeEventBusWire.of(),
            ForgeEventCancellationCarrier.of()
    );
    
}
