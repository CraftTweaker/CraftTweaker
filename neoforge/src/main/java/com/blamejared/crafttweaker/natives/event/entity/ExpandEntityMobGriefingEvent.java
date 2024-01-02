package com.blamejared.crafttweaker.natives.event.entity;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.neoforged.neoforge.event.entity.EntityMobGriefingEvent;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/entity/EntityMobGriefingEvent")
@NativeTypeRegistration(value = EntityMobGriefingEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.entity.EntityMobGriefingEvent")
public class ExpandEntityMobGriefingEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<EntityMobGriefingEvent> BUS = IEventBus.direct(
            EntityMobGriefingEvent.class,
            NeoForgeEventBusWire.of()
    );
    
    
}
