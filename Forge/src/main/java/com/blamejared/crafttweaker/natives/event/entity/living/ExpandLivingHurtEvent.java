package com.blamejared.crafttweaker.natives.event.entity.living;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("forge/api/event/entity/living/LivingHurtEvent")
@NativeTypeRegistration(value = LivingHurtEvent.class, zenCodeName = "crafttweaker.forge.api.event.entity.living.LivingHurtEvent")
public class ExpandLivingHurtEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<LivingHurtEvent> BUS = IEventBus.cancelable(
            LivingHurtEvent.class,
            ForgeEventBusWire.of(),
            ForgeEventCancellationCarrier.of()
    );
    
    @ZenCodeType.Getter("source")
    public static DamageSource getSource(LivingHurtEvent internal) {
        
        return internal.getSource();
    }
    
    @ZenCodeType.Getter("amount")
    public static float getAmount(LivingHurtEvent internal) {
        
        return internal.getAmount();
    }
    
    @ZenCodeType.Setter("amount")
    public static void setAmount(LivingHurtEvent internal, float amount) {
        
        internal.setAmount(amount);
    }
    
}
