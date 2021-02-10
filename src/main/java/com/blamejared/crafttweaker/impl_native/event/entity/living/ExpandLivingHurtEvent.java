package com.blamejared.crafttweaker.impl_native.event.entity.living;

import com.blamejared.crafttweaker_annotations.annotations.EventCancelable;
import org.openzen.zencode.java.ZenCodeType;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;

import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

/**
 * This event is fired just before an entity is hurt. This allows you to modify
 * the damage received, cancel the attack, or run additional effects.
 * 
 * @docParam this event
 */
@ZenRegister
@Document("vanilla/api/event/entity/living/MCLivingHurtEvent")
@EventCancelable(canceledDescription = "the entity is not hurt")
@NativeTypeRegistration(value = LivingHurtEvent.class, zenCodeName = "crafttweaker.api.event.entity.living.MCLivingHurtEvent")
public class ExpandLivingHurtEvent {
    
	/**
	 * Gets the source of the damage.
	 * 
	 * @return The source of the damage.
	 */
    @ZenCodeType.Method
    @ZenCodeType.Getter("source")
    public static DamageSource getSource(LivingHurtEvent internal) {
        return internal.getSource();
    }
    
	/**
	 * Gets the amount of damage.
	 * 
	 * @return The amount of damage.
	 */
    @ZenCodeType.Method
    @ZenCodeType.Getter("amount")
    public static float getAmount(LivingHurtEvent internal) {
        return internal.getAmount();
    }
    
	/**
	 * Sets the amount of damage.
	 * 
	 * @param amount The amount of damage.
	 * 
	 * @docParam amount 0.5
	 */
    @ZenCodeType.Method
    @ZenCodeType.Setter("amount")
    public static void setAmount(LivingHurtEvent internal, float amount) {
        internal.setAmount(amount);
    }
}