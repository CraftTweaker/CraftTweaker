package com.blamejared.crafttweaker.natives.event.entity.living;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraftforge.event.entity.living.LivingUseTotemEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("forge/api/event/entity/living/LivingUseTotemEvent")
@NativeTypeRegistration(value = LivingUseTotemEvent.class, zenCodeName = "crafttweaker.forge.api.event.entity.living.LivingUseTotemEvent")
public class ExpandLivingUseTotemEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<LivingUseTotemEvent> BUS = IEventBus.cancelable(
            LivingUseTotemEvent.class,
            ForgeEventBusWire.of(),
            ForgeEventCancellationCarrier.of()
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
