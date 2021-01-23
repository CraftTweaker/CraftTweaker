package com.blamejared.crafttweaker.impl_native.event.entity.living;

import org.openzen.zencode.java.ZenCodeType;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;

import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

/**
 * This event is fired just before an entity is killed. This allows you to run
 * additional logic or prevent the death.
 * 
 * @docParam this event
 */
@ZenRegister
@Document("vanilla/api/event/entity/living/MCLivingDeathEvent")
@NativeTypeRegistration(value = LivingDeathEvent.class, zenCodeName = "crafttweaker.api.event.entity.living.MCLivingDeathEvent")
public class ExpandLivingDeathEvent {
    
	/**
	 * Gets the source of the damage that killed the entity.
	 * 
	 * @return The source of the damage that killed the entity.
	 */
    @ZenCodeType.Method
    @ZenCodeType.Getter("source")
    public static DamageSource getSource(LivingDeathEvent internal) {
        return internal.getSource();
    }
}