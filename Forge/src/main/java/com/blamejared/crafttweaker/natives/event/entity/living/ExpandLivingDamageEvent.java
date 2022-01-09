package com.blamejared.crafttweaker.natives.event.entity.living;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docEvent canceled the Entity is not hurt. Used resources WILL NOT be restored.
 */
@ZenRegister
@Document("forge/api/event/living/LivingDamageEvent")
@NativeTypeRegistration(value = LivingDamageEvent.class, zenCodeName = "crafttweaker.api.event.living.LivingDamageEvent")
public class ExpandLivingDamageEvent {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("source")
    public static DamageSource getSource(LivingDamageEvent internal) {
        
        return internal.getSource();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("amount")
    public static float getAmount(LivingDamageEvent internal) {
        
        return internal.getAmount();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Setter("amount")
    public static void setAmount(LivingDamageEvent internal, float amount) {
        
        internal.setAmount(amount);
    }
    
}
