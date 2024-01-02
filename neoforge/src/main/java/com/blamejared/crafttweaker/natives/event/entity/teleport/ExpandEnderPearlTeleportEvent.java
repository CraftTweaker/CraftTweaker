package com.blamejared.crafttweaker.natives.event.entity.teleport;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.NeoForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/entity/teleport/EnderPearlTeleportEvent")
@NativeTypeRegistration(value = EntityTeleportEvent.EnderPearl.class, zenCodeName = "crafttweaker.neoforge.api.event.entity.teleport.EnderPearlTeleportEvent")
public class ExpandEnderPearlTeleportEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<EntityTeleportEvent.EnderPearl> BUS = IEventBus.cancelable(
            EntityTeleportEvent.EnderPearl.class,
            NeoForgeEventBusWire.of(),
            NeoForgeEventCancellationCarrier.of()
    );
    
    @ZenCodeType.Getter("pearlEntity")
    public static ThrownEnderpearl getPearlEntity(EntityTeleportEvent.EnderPearl internal) {
        
        return internal.getPearlEntity();
    }
    
    @ZenCodeType.Getter("player")
    public static ServerPlayer getPlayer(EntityTeleportEvent.EnderPearl internal) {
        
        return internal.getPlayer();
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Getter("hitResult")
    public static HitResult getHitResult(EntityTeleportEvent.EnderPearl internal) {
        
        return internal.getHitResult();
    }
    
    @ZenCodeType.Getter("attackDamage")
    public static float getAttackDamage(EntityTeleportEvent.EnderPearl internal) {
        
        return internal.getAttackDamage();
    }
    
    @ZenCodeType.Setter("attackDamage")
    public static void setAttackDamage(EntityTeleportEvent.EnderPearl internal, float attackDamage) {
        
        internal.setAttackDamage(attackDamage);
    }
    
}
