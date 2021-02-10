package com.blamejared.crafttweaker.impl_native.event.entity.living;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.impl.entity.MCEntityType;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.EventCancelable;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.living.LivingConversionEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * The event is triggered when an entity is trying to replace itself with another entity.
 * This event may trigger every tick even if it was cancelled last tick for entities like Zombies and Hoglins.
 */
@ZenRegister
@Document("vanilla/api/event/entity/living/MCLivingConversionPreEvent")
@EventCancelable(canceledDescription = "the replacement will not occur")
@NativeTypeRegistration(value = LivingConversionEvent.Pre.class, zenCodeName = "crafttweaker.api.event.living.MCLivingConversionPreEvent")
public class ExpandLivingConversionPreEvent {
    @ZenCodeType.Getter("outcome")
    public static MCEntityType getOutcome(LivingConversionEvent.Pre internal) {
        return new MCEntityType(internal.getOutcome());
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
