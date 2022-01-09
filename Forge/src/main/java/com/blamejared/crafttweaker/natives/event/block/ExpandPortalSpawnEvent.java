package com.blamejared.crafttweaker.natives.event.block;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.world.BlockEvent;

/**
 * @docEvent canceled the portal won't be spawned
 */
@ZenRegister
@Document("forge/api/event/block/PortalSpawnEvent")
@NativeTypeRegistration(value = BlockEvent.PortalSpawnEvent.class, zenCodeName = "crafttweaker.api.event.block.PortalSpawnEvent")
public class ExpandPortalSpawnEvent {
}
