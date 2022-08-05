package com.blamejared.crafttweaker.natives.event.entity.living.spawn;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * LivingSpawnEvent is fired when an event involving any spawning or despawning of an Entity occurs.
 *
 * This event is fired for all types of spawning or despawning mechanics, it is generally advised to use the specific teleport events
 * to target a specific mechanic instead of this event.
 */
@ZenRegister
@Document("forge/api/event/entity/living/spawn/LivingSpawnEvent")
@NativeTypeRegistration(value = LivingSpawnEvent.class, zenCodeName = "crafttweaker.api.event.entity.living.spawn.LivingSpawnEvent")
public class ExpandLivingSpawnEvent {
    
    @ZenCodeType.Getter("world")
    public static Level getWorld(LivingSpawnEvent internal) {
        
        if(internal.getLevel() instanceof Level level) {
            return level;
        }
        throw new IllegalArgumentException("LevelAccessor instance was not an instance of Level!");
    }
    
    @ZenCodeType.Getter("x")
    public static double getX(LivingSpawnEvent internal) {
        
        return internal.getX();
    }
    
    @ZenCodeType.Getter("y")
    public static double getY(LivingSpawnEvent internal) {
        
        return internal.getY();
    }
    
    @ZenCodeType.Getter("z")
    public static double getZ(LivingSpawnEvent internal) {
        
        return internal.getZ();
    }
    
}
