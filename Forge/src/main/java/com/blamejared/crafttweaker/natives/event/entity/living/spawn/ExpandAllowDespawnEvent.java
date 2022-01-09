package com.blamejared.crafttweaker.natives.event.entity.living.spawn;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.Event;

/**
 * Fired each tick for despawnable mobs to allow control over despawning.
 * {@link Event.Result#DEFAULT} will pass the mob on to vanilla despawn mechanics.
 * {@link Event.Result#ALLOW} will force the mob to despawn.
 * {@link Event.Result#DENY} will force the mob to remain.
 * This is fired every tick for every despawnable entity. Be efficient in your handlers.
 */
@ZenRegister
@Document("forge/api/event/entity/living/spawn/AllowDespawnEvent")
@NativeTypeRegistration(value = LivingSpawnEvent.AllowDespawn.class, zenCodeName = "crafttweaker.api.event.entity.living.spawn.AllowDespawnEvent")
public class ExpandAllowDespawnEvent {

}
