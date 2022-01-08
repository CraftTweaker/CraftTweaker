package com.blamejared.crafttweaker.natives.event.entity.living.teleport;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * EntityTeleportEvent is fired when an event involving any teleportation of an Entity occurs.
 *
 * This event is fired for all types of teleportation, it is generally advised to use the specific teleport events
 * to target a specific thing instead of this event.
 *
 * @docEvent canceled the teleport won't happen.
 */
@ZenRegister
@Document("vanilla/api/event/entity/living/teleport/EntityTeleportEvent")
@NativeTypeRegistration(value = EntityTeleportEvent.class, zenCodeName = "crafttweaker.api.event.living.teleport.EntityTeleportEvent")
public class ExpandEntityTeleportEvent {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("targetX")
    public static double getTargetX(EntityTeleportEvent internal) {
        
        return internal.getTargetX();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Setter("targetX")
    public static void setTargetX(EntityTeleportEvent internal, double targetX) {
        
        internal.setTargetX(targetX);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("targetY")
    public static double getTargetY(EntityTeleportEvent internal) {
        
        return internal.getTargetY();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Setter("targetY")
    public static void setTargetY(EntityTeleportEvent internal, double targetY) {
        
        internal.setTargetY(targetY);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("targetZ")
    public static double getTargetZ(EntityTeleportEvent internal) {
        
        return internal.getTargetZ();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Setter("targetZ")
    public static void setTargetZ(EntityTeleportEvent internal, double targetZ) {
        
        internal.setTargetZ(targetZ);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("target")
    public static Vec3 getTarget(EntityTeleportEvent internal) {
        
        return internal.getTarget();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("prevX")
    public static double getPrevX(EntityTeleportEvent internal) {
        
        return internal.getPrevX();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("prevY")
    public static double getPrevY(EntityTeleportEvent internal) {
        
        return internal.getPrevY();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("prevZ")
    public static double getPrevZ(EntityTeleportEvent internal) {
        
        return internal.getPrevZ();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("prev")
    public static Vec3 getPrev(EntityTeleportEvent internal) {
        
        return internal.getPrev();
    }
    
}
