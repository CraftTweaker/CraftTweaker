package com.blamejared.crafttweaker.natives.event.entity.living.spawn;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.BaseSpawner;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Fires before mob spawn events.
 *
 * Result is significant:
 * DEFAULT: use vanilla spawn rules
 * ALLOW:   allow the spawn
 * DENY:    deny the spawn
 */
@ZenRegister
@Document("forge/api/event/entity/living/spawn/CheckSpawnEvent")
@NativeTypeRegistration(value = LivingSpawnEvent.CheckSpawn.class, zenCodeName = "crafttweaker.api.event.entity.living.spawn.CheckSpawnEvent")
public class ExpandCheckSpawnEvent {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isSpawner")
    public static boolean isSpawner(LivingSpawnEvent.CheckSpawn internal) {
        
        return internal.isSpawner();
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    @ZenCodeType.Getter("spawner")
    public static BaseSpawner getSpawner(LivingSpawnEvent.CheckSpawn internal) {
        
        return internal.getSpawner();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("spawnReason")
    public static MobSpawnType getSpawnReason(LivingSpawnEvent.CheckSpawn internal) {
        
        return internal.getSpawnReason();
    }
    
}
