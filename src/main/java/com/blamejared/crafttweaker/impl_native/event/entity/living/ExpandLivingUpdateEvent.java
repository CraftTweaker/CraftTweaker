package com.blamejared.crafttweaker.impl_native.event.entity.living;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.living.LivingEvent;

/**
 * @docEvent canceled the Entity does not update.
 */
@ZenRegister
@Document("vanilla/api/event/entity/living/MCLivingUpdateEvent")
@NativeTypeRegistration(value = LivingEvent.LivingUpdateEvent.class, zenCodeName = "crafttweaker.api.event.living.MCLivingUpdateEvent")
public class ExpandLivingUpdateEvent {

}
