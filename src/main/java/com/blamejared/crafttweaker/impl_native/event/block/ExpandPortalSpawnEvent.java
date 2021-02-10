package com.blamejared.crafttweaker.impl_native.event.block;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.EventCancelable;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.world.BlockEvent;

@ZenRegister
@Document("vanilla/api/event/block/MCPortalSpawnEvent")
@EventCancelable(canceledDescription = "the portal won't be spawned")
@NativeTypeRegistration(value = BlockEvent.PortalSpawnEvent.class, zenCodeName = "crafttweaker.api.event.block.MCPortalSpawnEvent")
public class ExpandPortalSpawnEvent {
}
