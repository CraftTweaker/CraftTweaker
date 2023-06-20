package com.blamejared.crafttweaker.natives.event.entity.teleport;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.EntityTeleportEvent;

@ZenRegister
@ZenEvent
@Document("forge/api/event/entity/teleport/EntitySpreadPlayersCommandTeleportEvent")
@NativeTypeRegistration(value = EntityTeleportEvent.SpreadPlayersCommand.class, zenCodeName = "crafttweaker.forge.api.event.entity.teleport.EntitySpreadPlayersCommandTeleportEvent")
public class ExpandEntitySpreadPlayersCommandEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<EntityTeleportEvent.SpreadPlayersCommand> BUS = IEventBus.cancelable(
            EntityTeleportEvent.SpreadPlayersCommand.class,
            ForgeEventBusWire.of(),
            ForgeEventCancellationCarrier.of()
    );
    
}
