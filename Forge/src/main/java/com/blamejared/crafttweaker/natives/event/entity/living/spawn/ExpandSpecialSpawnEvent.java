package com.blamejared.crafttweaker.natives.event.entity.living.spawn;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.BaseSpawner;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * SpecialSpawn is fired when an Entity is to be spawned.
 *
 * @docEvent cancled the Entity will not be spawned.
 */
@ZenRegister
@Document("vanilla/api/event/entity/living/spawn/SpecialSpawnEvent")
@NativeTypeRegistration(value = LivingSpawnEvent.SpecialSpawn.class, zenCodeName = "crafttweaker.api.event.entity.living.spawn.SpecialSpawnEvent")
public class ExpandSpecialSpawnEvent {
    
    
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    @ZenCodeType.Getter("spawner")
    public static BaseSpawner getSpawner(LivingSpawnEvent.SpecialSpawn internal) {
        
        return internal.getSpawner();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("spawnReason")
    public static MobSpawnType getSpawnReason(LivingSpawnEvent.SpecialSpawn internal) {
        
        return internal.getSpawnReason();
    }
    
}
