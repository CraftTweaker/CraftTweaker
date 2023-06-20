package com.blamejared.crafttweaker.natives.event.entity.living.spawn;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;

@ZenRegister
@ZenEvent
@Document("forge/api/event/entity/living/spawn/LivingAllowDespawnEvent")
@NativeTypeRegistration(value = LivingSpawnEvent.AllowDespawn.class, zenCodeName = "crafttweaker.forge.api.event.entity.living.spawn.LivingAllowDespawnEvent")
public class ExpandLivingAllowDespawnEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<LivingSpawnEvent.AllowDespawn> BUS = IEventBus.direct(
            LivingSpawnEvent.AllowDespawn.class,
            ForgeEventBusWire.of()
    );
    
}
