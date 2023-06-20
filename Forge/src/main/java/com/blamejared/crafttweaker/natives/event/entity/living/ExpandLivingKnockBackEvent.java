package com.blamejared.crafttweaker.natives.event.entity.living;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("forge/api/event/entity/living/LivingKnockBackEvent")
@NativeTypeRegistration(value = LivingKnockBackEvent.class, zenCodeName = "crafttweaker.forge.api.event.entity.living.LivingKnockBackEvent")
public class ExpandLivingKnockBackEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<LivingKnockBackEvent> BUS = IEventBus.cancelable(
            LivingKnockBackEvent.class,
            ForgeEventBusWire.of(),
            ForgeEventCancellationCarrier.of()
    );
    
    @ZenCodeType.Getter("strength")
    public static float getStrength(LivingKnockBackEvent internal) {
        
        return internal.getStrength();
    }
    
    @ZenCodeType.Getter("ratioX")
    public static double getRatioX(LivingKnockBackEvent internal) {
        
        return internal.getRatioX();
    }
    
    @ZenCodeType.Getter("ratioZ")
    public static double getRatioZ(LivingKnockBackEvent internal) {
        
        return internal.getRatioZ();
    }
    
    @ZenCodeType.Getter("originalStrength")
    public static float getOriginalStrength(LivingKnockBackEvent internal) {
        
        return internal.getOriginalStrength();
    }
    
    @ZenCodeType.Getter("originalRatioX")
    public static double getOriginalRatioX(LivingKnockBackEvent internal) {
        
        return internal.getOriginalRatioX();
    }
    
    @ZenCodeType.Getter("originalRatioZ")
    public static double getOriginalRatioZ(LivingKnockBackEvent internal) {
        
        return internal.getOriginalRatioZ();
    }
    
    @ZenCodeType.Setter("strength")
    public static void setStrength(LivingKnockBackEvent internal, float strength) {
        
        internal.setStrength(strength);
    }
    
    @ZenCodeType.Setter("ratioX")
    public static void setRatioX(LivingKnockBackEvent internal, double ratioX) {
        
        internal.setRatioX(ratioX);
    }
    
    @ZenCodeType.Setter("ratioZ")
    public static void setRatioZ(LivingKnockBackEvent internal, double ratioZ) {
        
        internal.setRatioZ(ratioZ);
    }
    
}
