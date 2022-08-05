package com.blamejared.crafttweaker.natives.event.entity.living;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.living.LivingEvent;

/**
 * @docEvent canceled the Entity does not update.
 */
@ZenRegister
@Document("forge/api/event/entity/living/LivingTickEvent")
@NativeTypeRegistration(value = LivingEvent.LivingTickEvent.class, zenCodeName = "crafttweaker.api.event.living.LivingTickEvent")
public class ExpandLivingUpdateEvent {

}
