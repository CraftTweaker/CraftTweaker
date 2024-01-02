package com.blamejared.crafttweaker.natives.event.entity.teleport;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.NeoForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/entity/teleport/EntityCommandTeleportEvent")
@NativeTypeRegistration(value = EntityTeleportEvent.TeleportCommand.class, zenCodeName = "crafttweaker.neoforge.api.event.entity.teleport.EntityCommandTeleportEvent")
public class ExpandEntityCommandTeleportEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<EntityTeleportEvent.TeleportCommand> BUS = IEventBus.cancelable(
            EntityTeleportEvent.TeleportCommand.class,
            NeoForgeEventBusWire.of(),
            NeoForgeEventCancellationCarrier.of()
    );
    
}
