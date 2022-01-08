package com.blamejared.crafttweaker.natives.event.entity.living;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docEvent canceled the Entity does not fall (no fall damage is inflicted).
 */
@ZenRegister
@Document("vanilla/api/event/entity/living/LivingFallEvent")
@NativeTypeRegistration(value = LivingFallEvent.class, zenCodeName = "crafttweaker.api.event.living.LivingFallEvent")
public class ExpandLivingFallEvent {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("distance")
    public static float getDistance(LivingFallEvent internal) {
        
        return internal.getDistance();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("damageMultiplier")
    public static float getDamageMultiplier(LivingFallEvent internal) {
        
        return internal.getDamageMultiplier();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Setter("distance")
    public static void setDistance(LivingFallEvent internal, int value) {
        
        internal.setDistance(value);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Setter("damageMultiplier")
    public static void setDamageMultiplier(LivingFallEvent internal, int value) {
        
        internal.setDamageMultiplier(value);
    }
    
}
