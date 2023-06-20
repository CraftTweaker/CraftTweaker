package com.blamejared.crafttweaker.natives.event.entity.living.spawn;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("forge/api/event/entity/living/spawn/LivingSpawnEvent")
@NativeTypeRegistration(value = LivingSpawnEvent.class, zenCodeName = "crafttweaker.forge.api.event.entity.living.spawn.LivingSpawnEvent")
public class ExpandLivingSpawnEvent {
    
    @ZenCodeType.Getter("entity")
    public static Mob getEntity(LivingSpawnEvent internal) {
        
        return internal.getEntity();
    }
    
    @ZenCodeType.Getter("level")
    public static LevelAccessor getLevel(LivingSpawnEvent internal) {
        
        return internal.getLevel();
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
