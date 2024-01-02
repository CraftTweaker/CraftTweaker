package com.blamejared.crafttweaker.natives.event.entity.living;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.NeoForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/entity/living/LivingFallEvent")
@NativeTypeRegistration(value = LivingFallEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.entity.living.LivingFallEvent")
public class ExpandLivingFallEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<LivingFallEvent> BUS = IEventBus.cancelable(
            LivingFallEvent.class,
            NeoForgeEventBusWire.of(),
            NeoForgeEventCancellationCarrier.of()
    );
    
    @ZenCodeType.Getter("distance")
    public static float getDistance(LivingFallEvent internal) {
        
        return internal.getDistance();
    }
    
    @ZenCodeType.Setter("distance")
    public static void setDistance(LivingFallEvent internal, float distance) {
        
        internal.setDistance(distance);
    }
    
    @ZenCodeType.Getter("damageMultiplier")
    public static float getDamageMultiplier(LivingFallEvent internal) {
        
        return internal.getDamageMultiplier();
    }
    
    @ZenCodeType.Setter("damageMultiplier")
    public static void setDamageMultiplier(LivingFallEvent internal, float damageMultiplier) {
        
        internal.setDamageMultiplier(damageMultiplier);
    }
    
}
