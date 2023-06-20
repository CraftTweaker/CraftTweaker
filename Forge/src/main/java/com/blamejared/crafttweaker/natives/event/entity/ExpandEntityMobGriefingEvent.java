package com.blamejared.crafttweaker.natives.event.entity;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.EntityMobGriefingEvent;

@ZenRegister
@ZenEvent
@Document("forge/api/event/entity/EntityMobGriefingEvent")
@NativeTypeRegistration(value = EntityMobGriefingEvent.class, zenCodeName = "crafttweaker.forge.api.event.entity.EntityMobGriefingEvent")
public class ExpandEntityMobGriefingEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<EntityMobGriefingEvent> BUS = IEventBus.direct(
            EntityMobGriefingEvent.class,
            ForgeEventBusWire.of()
    );
    
    
}
