package com.blamejared.crafttweaker.natives.event.entity.living;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.NeoForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.neoforged.neoforge.event.entity.living.LivingUseTotemEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/entity/living/LivingUseTotemEvent")
@NativeTypeRegistration(value = LivingUseTotemEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.entity.living.LivingUseTotemEvent")
public class ExpandLivingUseTotemEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<LivingUseTotemEvent> BUS = IEventBus.cancelable(
            LivingUseTotemEvent.class,
            NeoForgeEventBusWire.of(),
            NeoForgeEventCancellationCarrier.of()
    );
    
    @ZenCodeType.Getter("source")
    public static DamageSource getSource(LivingUseTotemEvent internal) {
        
        return internal.getSource();
    }
    
    @ZenCodeType.Getter("totem")
    public static IItemStack getTotem(LivingUseTotemEvent internal) {
        
        return IItemStack.of(internal.getTotem());
    }
    
    @ZenCodeType.Getter("holdingHand")
    public static InteractionHand getHoldingHand(LivingUseTotemEvent internal) {
        
        return internal.getHandHolding();
    }
    
}
