package com.blamejared.crafttweaker.natives.event.entity.living;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * This event is fired just before an entity is killed. This allows you to run
 * additional logic or prevent the death.
 *
 * @docParam this event
 * @docEvent canceled the entity does not die.
 */
@ZenRegister
@Document("vanilla/api/event/entity/living/LivingDeathEvent")
@NativeTypeRegistration(value = LivingDeathEvent.class, zenCodeName = "crafttweaker.api.event.entity.living.LivingDeathEvent")
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