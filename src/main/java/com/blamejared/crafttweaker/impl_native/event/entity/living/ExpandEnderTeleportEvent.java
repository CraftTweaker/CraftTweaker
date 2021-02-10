package com.blamejared.crafttweaker.impl_native.event.entity.living;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.EventCancelable;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Event for when an Enderman/Shulker teleports or an ender pearl is used.
 */
@ZenRegister
@Document("vanilla/api/event/entity/living/MCEnderTeleportEvent")
@EventCancelable
@NativeTypeRegistration(value = EnderTeleportEvent.class, zenCodeName = "crafttweaker.api.event.living.MCEnderTeleportEvent")
public class ExpandEnderTeleportEvent {
    @ZenCodeType.Getter("targetX")
    public static double getTargetX(EnderTeleportEvent internal) {
        return internal.getTargetX();
    }
    
    @ZenCodeType.Getter("targetY")
    public static double getTargetY(EnderTeleportEvent internal) {
        return internal.getTargetY();
    }
    
    @ZenCodeType.Getter("targetZ")
    public static double getTargetZ(EnderTeleportEvent internal) {
        return internal.getTargetZ();
    }
    
    @ZenCodeType.Setter("targetX")
    public static void setTargetX(EnderTeleportEvent internal, double value) {
        internal.setTargetX(value);
    }
    
    @ZenCodeType.Setter("targetY")
    public static void setTargetY(EnderTeleportEvent internal, double value) {
        internal.setTargetY(value);
    }
    
    @ZenCodeType.Setter("targetZ")
    public static void setTargetZ(EnderTeleportEvent internal, double value) {
        internal.setTargetZ(value);
    }
    
    @ZenCodeType.Getter("attackDamage")
    public static float getAttackDamage(EnderTeleportEvent internal) {
        return internal.getAttackDamage();
    }
    
    @ZenCodeType.Setter("attackDamage")
    public static void setAttackDamage(EnderTeleportEvent internal, float value) {
        internal.setAttackDamage(value);
    }
}
