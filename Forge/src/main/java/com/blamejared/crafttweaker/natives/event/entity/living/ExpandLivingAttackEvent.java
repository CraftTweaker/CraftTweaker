package com.blamejared.crafttweaker.natives.event.entity.living;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * LivingAttackEvent is fired when a living Entity is attacked.
 *
 * @docEvent canceled the entity does not take attack damage.
 */
@ZenRegister
@Document("forge/api/event/entity/living/LivingAttackEvent")
@NativeTypeRegistration(value = LivingAttackEvent.class, zenCodeName = "crafttweaker.api.event.entity.living.LivingAttackEvent")
public class ExpandLivingAttackEvent {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("source")
    @ZenCodeType.Nullable
    public static DamageSource getSource(LivingAttackEvent internal) {
        
        return internal.getSource();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("amount")
    public static float getAmount(LivingAttackEvent internal) {
        
        return internal.getAmount();
    }
    
}
