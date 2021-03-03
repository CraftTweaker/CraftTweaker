package com.blamejared.crafttweaker.impl_native.event.entity.living;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docEvent canceled the Entity does not fall.
 */
@ZenRegister
@Document("vanilla/api/event/entity/living/MCLivingFallEvent")
@NativeTypeRegistration(value = LivingFallEvent.class, zenCodeName = "crafttweaker.api.event.living.MCLivingFallEvent")
public class ExpandLivingFallEvent {
    
    @ZenCodeType.Getter("distance")
    public static float getDistance(LivingFallEvent internal) {
        
        return internal.getDistance();
    }
    
    @ZenCodeType.Getter("damageMultiplier")
    public static float getDamageMultiplier(LivingFallEvent internal) {
        
        return internal.getDamageMultiplier();
    }
    
    @ZenCodeType.Setter("distance")
    public static void setDistance(LivingFallEvent internal, int value) {
        
        internal.setDistance(value);
    }
    
    @ZenCodeType.Setter("damageMultiplier")
    public static void setDamageMultiplier(LivingFallEvent internal, int value) {
        
        internal.setDamageMultiplier(value);
    }
    
}
