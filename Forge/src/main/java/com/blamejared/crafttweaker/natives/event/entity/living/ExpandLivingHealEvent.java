package com.blamejared.crafttweaker.natives.event.entity.living;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * This event is fired just before an entity is healed. This allows you to
 * modify the amount of healing or prevent it all together by canceling the
 * event.
 *
 * @docParam this event
 * @docEvent canceled the entity is not healed
 */
@ZenRegister
@Document("vanilla/api/event/entity/living/LivingHealEvent")
@NativeTypeRegistration(value = LivingHealEvent.class, zenCodeName = "crafttweaker.api.event.entity.living.LivingHealEvent")
public class ExpandLivingHealEvent {
    
    /**
     * Gets the amount of healing.
     *
     * @return The amount of damage to heal.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("amount")
    public static float getAmount(LivingHealEvent internal) {
        
        return internal.getAmount();
    }
    
    /**
     * Sets the amount of healing.
     *
     * @param amount The amount of damage to heal.
     *
     * @docParam amount 0.5
     */
    @ZenCodeType.Method
    @ZenCodeType.Setter("amount")
    public static void setAmount(LivingHealEvent internal, float amount) {
        
        internal.setAmount(amount);
    }
    
}