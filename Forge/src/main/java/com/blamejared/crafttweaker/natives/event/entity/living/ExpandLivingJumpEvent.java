package com.blamejared.crafttweaker.natives.event.entity.living;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.living.LivingEvent;

@ZenRegister
@ZenEvent
@Document("forge/api/event/entity/living/LivingJumpEvent")
@NativeTypeRegistration(value = LivingEvent.LivingJumpEvent.class, zenCodeName = "crafttweaker.forge.api.event.entity.living.LivingJumpEvent")
public class ExpandLivingJumpEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<LivingEvent.LivingJumpEvent> BUS = IEventBus.direct(
            LivingEvent.LivingJumpEvent.class,
            ForgeEventBusWire.of()
    );
    
}
