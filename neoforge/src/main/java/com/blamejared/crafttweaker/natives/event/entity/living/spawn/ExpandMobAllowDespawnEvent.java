package com.blamejared.crafttweaker.natives.event.entity.living.spawn;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.neoforged.neoforge.event.entity.living.MobSpawnEvent;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/entity/living/spawn/AllowMobDespawnEvent")
@NativeTypeRegistration(value = MobSpawnEvent.AllowDespawn.class, zenCodeName = "crafttweaker.neoforge.api.event.entity.living.spawn.AllowMobDespawnEvent")
public class ExpandMobAllowDespawnEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<MobSpawnEvent.AllowDespawn> BUS = IEventBus.direct(
            MobSpawnEvent.AllowDespawn.class,
            NeoForgeEventBusWire.of()
    );
    
}
