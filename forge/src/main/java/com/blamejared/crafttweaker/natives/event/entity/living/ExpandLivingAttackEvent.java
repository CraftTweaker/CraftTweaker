package com.blamejared.crafttweaker.natives.event.entity.living;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("forge/api/event/entity/living/LivingAttackEvent")
@NativeTypeRegistration(value = LivingAttackEvent.class, zenCodeName = "crafttweaker.forge.api.event.entity.living.LivingAttackEvent")
public class ExpandLivingAttackEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<LivingAttackEvent> BUS = IEventBus.cancelable(
            LivingAttackEvent.class,
            ForgeEventBusWire.of(),
            ForgeEventCancellationCarrier.of()
    );
    
    @ZenCodeType.Getter("source")
    public static DamageSource getSource(LivingAttackEvent internal) {
        
        return internal.getSource();
    }
    
    @ZenCodeType.Getter("amount")
    public static float getAmount(LivingAttackEvent internal) {
        
        return internal.getAmount();
    }
    
}
