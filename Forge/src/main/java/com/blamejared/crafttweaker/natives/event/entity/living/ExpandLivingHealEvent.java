package com.blamejared.crafttweaker.natives.event.entity.living;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("forge/api/event/entity/living/LivingHealEvent")
@NativeTypeRegistration(value = LivingHealEvent.class, zenCodeName = "crafttweaker.forge.api.event.entity.living.LivingHealEvent")
public class ExpandLivingHealEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<LivingHealEvent> BUS = IEventBus.cancelable(
            LivingHealEvent.class,
            ForgeEventBusWire.of(),
            ForgeEventCancellationCarrier.of()
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
