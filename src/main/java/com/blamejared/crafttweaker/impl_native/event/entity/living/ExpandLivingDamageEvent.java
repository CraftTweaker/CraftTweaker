package com.blamejared.crafttweaker.impl_native.event.entity.living;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docEvent canceled the Entity is not hurt. Used resources WILL NOT be restored.
 */
@ZenRegister
@Document("vanilla/api/event/living/MCLivingDamageEvent")
@NativeTypeRegistration(value = LivingDamageEvent.class, zenCodeName = "crafttweaker.api.event.living.MCLivingDamageEvent")
public class ExpandLivingDamageEvent {
    
    @ZenCodeType.Getter("source")
    public static DamageSource getSource(LivingDamageEvent internal) {
        
        return internal.getSource();
    }
    
    @ZenCodeType.Getter("amount")
    public static float getAmount(LivingDamageEvent internal) {
        
        return internal.getAmount();
    }
    
    @ZenCodeType.Setter("amount")
    public static void setAmount(LivingDamageEvent internal, float amount) {
        
        internal.setAmount(amount);
    }
    
}
