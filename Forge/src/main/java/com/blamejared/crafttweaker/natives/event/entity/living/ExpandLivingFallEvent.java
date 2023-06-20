package com.blamejared.crafttweaker.natives.event.entity.living;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("forge/api/event/entity/living/LivingFallEvent")
@NativeTypeRegistration(value = LivingFallEvent.class, zenCodeName = "crafttweaker.forge.api.event.entity.living.LivingFallEvent")
public class ExpandLivingFallEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<LivingFallEvent> BUS = IEventBus.cancelable(
            LivingFallEvent.class,
            ForgeEventBusWire.of(),
            ForgeEventCancellationCarrier.of()
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
