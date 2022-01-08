package com.blamejared.crafttweaker.natives.event.entity.living;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docParam this event
 * @docEvent canceled the entity is not knocked back.
 */
@ZenRegister
@Document("vanilla/api/event/entity/living/LivingKnockBackEvent")
@NativeTypeRegistration(value = LivingKnockBackEvent.class, zenCodeName = "crafttweaker.api.event.living.LivingKnockBackEvent")
public class ExpandLivingKnockBackEvent {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("strength")
    public static float getStrength(LivingKnockBackEvent internal) {
        
        return internal.getStrength();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("originStrength")
    public static float getOriginStrength(LivingKnockBackEvent internal) {
        
        return internal.getOriginalStrength();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("ratioX")
    public static double getRatioX(LivingKnockBackEvent internal) {
        
        return internal.getRatioX();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("ratioZ")
    public static double getRatioZ(LivingKnockBackEvent internal) {
        
        return internal.getRatioZ();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("originRatioX")
    public static double getOriginRatioX(LivingKnockBackEvent internal) {
        
        return internal.getOriginalRatioX();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("originRatioZ")
    public static double getOriginRatioZ(LivingKnockBackEvent internal) {
        
        return internal.getOriginalRatioZ();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Setter("strength")
    public static void setStrength(LivingKnockBackEvent internal, float value) {
        
        internal.setStrength(value);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Setter("ratioX")
    public static void setRatioX(LivingKnockBackEvent internal, double value) {
        
        internal.setRatioX(value);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Setter("ratioZ")
    public static void setRatioZ(LivingKnockBackEvent internal, double value) {
        
        internal.setRatioZ(value);
    }
    
}
