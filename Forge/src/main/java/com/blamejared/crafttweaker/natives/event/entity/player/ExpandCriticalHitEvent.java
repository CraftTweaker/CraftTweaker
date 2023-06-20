package com.blamejared.crafttweaker.natives.event.entity.player;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("forge/api/event/entity/player/CriticalHitEvent")
@NativeTypeRegistration(value = CriticalHitEvent.class, zenCodeName = "crafttweaker.forge.api.event.entity.player.CriticalHitEvent")
public class ExpandCriticalHitEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<CriticalHitEvent> BUS = IEventBus.direct(
            CriticalHitEvent.class,
            ForgeEventBusWire.of()
    );
    
    @ZenCodeType.Getter("target")
    public static Entity getTarget(CriticalHitEvent internal) {
        
        return internal.getTarget();
    }
    
    @ZenCodeType.Setter("damageModifier")
    public static void setDamageModifier(CriticalHitEvent internal, float mod) {
        
        internal.setDamageModifier(mod);
    }
    
    @ZenCodeType.Getter("damageModifier")
    public static float getDamageModifier(CriticalHitEvent internal) {
        
        return internal.getDamageModifier();
    }
    
    @ZenCodeType.Getter("oldDamageModifier")
    public static float getOldDamageModifier(CriticalHitEvent internal) {
        
        return internal.getOldDamageModifier();
    }
    
    @ZenCodeType.Getter("isVanillaCritical")
    public static boolean isVanillaCritical(CriticalHitEvent internal) {
        
        return internal.isVanillaCritical();
    }
    
}
