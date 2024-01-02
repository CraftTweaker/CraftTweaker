package com.blamejared.crafttweaker.natives.event.entity.living.spawn;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.ServerLevelAccessor;
import net.neoforged.neoforge.event.entity.living.MobSpawnEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("neoforge/api/event/entity/living/spawn/MobSpawnEvent")
@NativeTypeRegistration(value = MobSpawnEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.entity.living.spawn.MobSpawnEvent")
public class ExpandMobSpawnEvent {
    
    @ZenCodeType.Getter("entity")
    public static Mob getEntity(MobSpawnEvent internal) {
        
        return internal.getEntity();
    }
    
    @ZenCodeType.Getter("level")
    public static ServerLevelAccessor getLevel(MobSpawnEvent internal) {
        
        return internal.getLevel();
    }
    
    @ZenCodeType.Getter("x")
    public static double getX(MobSpawnEvent internal) {
        
        return internal.getX();
    }
    
    @ZenCodeType.Getter("y")
    public static double getY(MobSpawnEvent internal) {
        
        return internal.getY();
    }
    
    @ZenCodeType.Getter("z")
    public static double getZ(MobSpawnEvent internal) {
        
        return internal.getZ();
    }
    
}
