package com.blamejared.crafttweaker.natives.event.entity.living.conversion;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.living.LivingConversionEvent;

/**
 * LivingConversionEvent is fired when an event involving entity conversion occures, such as when a Villager is struck by lightning turning it into a Witch.
 *
 * This event is fired for both phases of the conversion events, both Pre and Post, it is generally advised to use the specific events
 * to target a specific phase instead of this event.
 */
@ZenRegister
@Document("forge/api/event/entity/living/conversion/LivingConversionEvent")
@NativeTypeRegistration(value = LivingConversionEvent.class, zenCodeName = "crafttweaker.api.event.entity.living.conversion.LivingConversionEvent")
public class ExpandLivingConversionEvent {
    
}
