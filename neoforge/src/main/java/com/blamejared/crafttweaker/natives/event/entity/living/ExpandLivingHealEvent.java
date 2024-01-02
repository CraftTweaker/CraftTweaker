package com.blamejared.crafttweaker.natives.event.entity.living;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.NeoForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/entity/living/LivingHealEvent")
@NativeTypeRegistration(value = LivingHealEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.entity.living.LivingHealEvent")
public class ExpandLivingHealEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<LivingHealEvent> BUS = IEventBus.cancelable(
            LivingHealEvent.class,
            NeoForgeEventBusWire.of(),
            NeoForgeEventCancellationCarrier.of()
    );
    
    @ZenCodeType.Getter("amount")
    public static float getAmount(LivingHealEvent internal) {
        
        return internal.getAmount();
    }
    
    @ZenCodeType.Setter("amount")
    public static void setAmount(LivingHealEvent internal, float amount) {
        
        internal.setAmount(amount);
    }
    
}
