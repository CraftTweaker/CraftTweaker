package com.blamejared.crafttweaker.impl_native.event.entity.living;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docParam this event
 * @docEvent canceled the entity is not knocked back.
 */
@ZenRegister
@Document("vanilla/api/event/entity/living/MCLivingKnockBackEvent")
@NativeTypeRegistration(value = LivingKnockBackEvent.class, zenCodeName = "crafttweaker.api.event.living.MCLivingKnockBackEvent")
public class ExpandLivingKnockBackEvent {
    
    @ZenCodeType.Getter("strength")
    public static float getStrength(LivingKnockBackEvent internal) {
        
        return internal.getStrength();
    }
    
    @ZenCodeType.Getter("originStrength")
    public static float getOriginStrength(LivingKnockBackEvent internal) {
        
        return internal.getOriginalStrength();
    }
    
    @ZenCodeType.Getter("ratioX")
    public static double getRatioX(LivingKnockBackEvent internal) {
        
        return internal.getRatioX();
    }
    
    @ZenCodeType.Getter("ratioZ")
    public static double getRatioZ(LivingKnockBackEvent internal) {
        
        return internal.getRatioZ();
    }
    
    @ZenCodeType.Getter("originRatioX")
    public static double getOriginRatioX(LivingKnockBackEvent internal) {
        
        return internal.getOriginalRatioX();
    }
    
    @ZenCodeType.Getter("originRatioZ")
    public static double getOriginRatioZ(LivingKnockBackEvent internal) {
        
        return internal.getOriginalRatioZ();
    }
    
    @ZenCodeType.Setter("strength")
    public static void setStrength(LivingKnockBackEvent internal, float value) {
        
        internal.setStrength(value);
    }
    
    @ZenCodeType.Setter("ratioX")
    public static void setRatioX(LivingKnockBackEvent internal, double value) {
        
        internal.setRatioX(value);
    }
    
    @ZenCodeType.Setter("ratioZ")
    public static void setRatioZ(LivingKnockBackEvent internal, double value) {
        
        internal.setRatioZ(value);
    }
    
}
