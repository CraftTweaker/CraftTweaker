package com.blamejared.crafttweaker.natives.event.entity.living;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("forge/api/event/entity/living/ShieldBlockEvent")
@NativeTypeRegistration(value = ShieldBlockEvent.class, zenCodeName = "crafttweaker.forge.api.event.entity.living.ShieldBlockEvent")
public class ExpandShieldBlockEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<ShieldBlockEvent> BUS = IEventBus.cancelable(
            ShieldBlockEvent.class,
            ForgeEventBusWire.of(),
            ForgeEventCancellationCarrier.of()
    );
    
    @ZenCodeType.Getter("damageSource")
    public static DamageSource getDamageSource(ShieldBlockEvent internal) {
        
        return internal.getDamageSource();
    }
    
    @ZenCodeType.Getter("originalBlockedDamage")
    public static float getOriginalBlockedDamage(ShieldBlockEvent internal) {
        
        return internal.getOriginalBlockedDamage();
    }
    
    @ZenCodeType.Getter("blockedDamage")
    public static float getBlockedDamage(ShieldBlockEvent internal) {
        
        return internal.getBlockedDamage();
    }
    
    @ZenCodeType.Getter("shieldTakesDamage")
    public static boolean shieldTakesDamage(ShieldBlockEvent internal) {
        
        return internal.shieldTakesDamage();
    }
    
    @ZenCodeType.Setter("blockedDamage")
    public static void setBlockedDamage(ShieldBlockEvent internal, float blocked) {
        
        internal.setBlockedDamage(blocked);
    }
    
    @ZenCodeType.Setter("shieldTakesDamage")
    public static void setShieldTakesDamage(ShieldBlockEvent internal, boolean damage) {
        
        internal.setShieldTakesDamage(damage);
    }
    
}
