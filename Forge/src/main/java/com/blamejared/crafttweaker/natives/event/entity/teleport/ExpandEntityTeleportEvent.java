package com.blamejared.crafttweaker.natives.event.entity.teleport;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("forge/api/event/entity/teleport/EntityTeleportEvent")
@NativeTypeRegistration(value = EntityTeleportEvent.class, zenCodeName = "crafttweaker.forge.api.event.entity.teleport.EntityTeleportEvent")
public class ExpandEntityTeleportEvent {
    
    @ZenCodeType.Getter("targetX")
    public static double getTargetX(EntityTeleportEvent internal) {
        
        return internal.getTargetX();
    }
    
    @ZenCodeType.Setter("targetX")
    public static void setTargetX(EntityTeleportEvent internal, double targetX) {
        
        internal.setTargetX(targetX);
    }
    
    @ZenCodeType.Getter("targetY")
    public static double getTargetY(EntityTeleportEvent internal) {
        
        return internal.getTargetY();
    }
    
    @ZenCodeType.Setter("targetY")
    public static void setTargetY(EntityTeleportEvent internal, double targetY) {
        
        internal.setTargetY(targetY);
    }
    
    @ZenCodeType.Getter("targetZ")
    public static double getTargetZ(EntityTeleportEvent internal) {
        
        return internal.getTargetZ();
    }
    
    @ZenCodeType.Setter("targetZ")
    public static void setTargetZ(EntityTeleportEvent internal, double targetZ) {
        
        internal.setTargetZ(targetZ);
    }
    
    @ZenCodeType.Getter("target")
    public static Vec3 getTarget(EntityTeleportEvent internal) {
        
        return internal.getTarget();
    }
    
    @ZenCodeType.Getter("prevX")
    public static double getPrevX(EntityTeleportEvent internal) {
        
        return internal.getPrevX();
    }
    
    @ZenCodeType.Getter("prevY")
    public static double getPrevY(EntityTeleportEvent internal) {
        
        return internal.getPrevY();
    }
    
    @ZenCodeType.Getter("prevZ")
    public static double getPrevZ(EntityTeleportEvent internal) {
        
        return internal.getPrevZ();
    }
    
    @ZenCodeType.Getter("prev")
    public static Vec3 getPrev(EntityTeleportEvent internal) {
        
        return internal.getPrev();
    }
    
}
