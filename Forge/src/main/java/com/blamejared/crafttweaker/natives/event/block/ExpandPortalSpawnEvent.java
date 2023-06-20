package com.blamejared.crafttweaker.natives.event.block;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.level.BlockEvent;

@ZenRegister
@ZenEvent
@Document("forge/api/event/block/PortalSpawnEvent")
@NativeTypeRegistration(value = BlockEvent.PortalSpawnEvent.class, zenCodeName = "crafttweaker.forge.api.event.block.PortalSpawnEvent")
public class ExpandPortalSpawnEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<BlockEvent.PortalSpawnEvent> BUS = IEventBus.cancelable(
            BlockEvent.PortalSpawnEvent.class,
            ForgeEventBusWire.of(),
            ForgeEventCancellationCarrier.of()
    );
    
}
