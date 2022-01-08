package com.blamejared.crafttweaker.natives.event.entity.living.teleport;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * EnderPearlTeleportEvent is fired before an Entity is teleported from a ThrownEnderpearl.
 *
 * @docEvent canceled the teleport won't happen.
 */
@ZenRegister
@Document("vanilla/api/event/entity/living/teleport/EnderPearlTeleportEvent")
@NativeTypeRegistration(value = EntityTeleportEvent.EnderPearl.class, zenCodeName = "crafttweaker.api.event.living.teleport.EnderPearlTeleportEvent")
public class ExpandEnderPearlTeleportEvent {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("pearlEntity")
    public static ThrownEnderpearl getPearlEntity(EntityTeleportEvent.EnderPearl internal) {
        
        return internal.getPearlEntity();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("player")
    public static ServerPlayer getPlayer(EntityTeleportEvent.EnderPearl internal) {
        
        return internal.getPlayer();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("attackDamage")
    public static float getAttackDamage(EntityTeleportEvent.EnderPearl internal) {
        
        return internal.getAttackDamage();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Setter("attackDamage")
    public static void setAttackDamage(EntityTeleportEvent.EnderPearl internal, float attackDamage) {
        
        internal.setAttackDamage(attackDamage);
    }
    
}
