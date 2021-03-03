package com.blamejared.crafttweaker.impl_native.event.entity.living;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * LivingAttackEvent is fired when a living Entity is attacked.
 *
 * @docEvent canceled the entity does not take attack damage.
 */
@ZenRegister
@Document("vanilla/api/event/entity/living/MCLivingAttackEvent")
@NativeTypeRegistration(value = LivingAttackEvent.class, zenCodeName = "crafttweaker.api.event.entity.living.MCLivingAttackEvent")
public class ExpandLivingAttackEvent {
    
    @ZenCodeType.Getter("source")
    public static DamageSource getSource(LivingAttackEvent internal) {
        
        return internal.getSource();
    }
    
    @ZenCodeType.Getter("amount")
    public static float getAmount(LivingAttackEvent internal) {
        
        return internal.getAmount();
    }
    
}
