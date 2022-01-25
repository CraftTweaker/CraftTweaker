package com.blamejared.crafttweaker.natives.event.entity.living.conversion;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.event.entity.living.LivingConversionEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * The event is triggered when an entity is trying to replace itself with another entity.
 * This event may trigger every tick even if it was cancelled last tick for entities like Zombies and Hoglins.
 *
 * @docEvent canceled the replacement will not occur
 */
@ZenRegister
@Document("forge/api/event/entity/living/conversion/LivingConversionPreEvent")
@NativeTypeRegistration(value = LivingConversionEvent.Pre.class, zenCodeName = "crafttweaker.api.event.living.conversion.LivingConversionPreEvent")
public class ExpandLivingConversionPreEvent {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("outcome")
    public static EntityType getOutcome(LivingConversionEvent.Pre internal) {
        
        return internal.getOutcome();
    }
    
    /**
     * Sets the conversion timer, by changing this it prevents the
     * event being triggered every tick
     * Do note the timer of some of the entities are increments, but
     * some of them are decrements
     * Not every conversion is applicable for this
     */
    @ZenCodeType.Method
    public static void setConversionTimer(LivingConversionEvent.Pre internal, int ticks) {
        
        internal.setConversionTimer(ticks);
    }
    
}
