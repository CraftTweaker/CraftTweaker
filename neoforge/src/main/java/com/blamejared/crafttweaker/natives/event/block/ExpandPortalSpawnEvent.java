package com.blamejared.crafttweaker.natives.event.block;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.NeoForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.neoforged.neoforge.event.level.BlockEvent;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/block/PortalSpawnEvent")
@NativeTypeRegistration(value = BlockEvent.PortalSpawnEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.block.PortalSpawnEvent")
public class ExpandPortalSpawnEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<BlockEvent.PortalSpawnEvent> BUS = IEventBus.cancelable(
            BlockEvent.PortalSpawnEvent.class,
            NeoForgeEventBusWire.of(),
            NeoForgeEventCancellationCarrier.of()
    );
    
}
