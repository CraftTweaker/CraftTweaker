package com.blamejared.crafttweaker.impl_native.event.entity.living;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/event/entity/living/MCLivingSpawnEvent")
@NativeTypeRegistration(value = LivingSpawnEvent.class, zenCodeName = "crafttweaker.api.event.entity.living.MCLivingSpawnEvent")
public class ExpandLivingSpawnEvent {
    
    @ZenCodeType.Getter("world")
    public static World getWorld(LivingSpawnEvent internal) {
        
        if(internal.getWorld() instanceof World) {
            return (World) internal.getWorld();
        }
        throw new IllegalStateException("the event is not fired on server or client world!");
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
    
    // TODO: specific events
}
