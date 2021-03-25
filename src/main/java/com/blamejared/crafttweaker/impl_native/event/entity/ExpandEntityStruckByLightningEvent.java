package com.blamejared.crafttweaker.impl_native.event.entity;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;

/**
 * @docEvent canceled the entity is not struck by the lightening.
 */
@ZenRegister
@Document("vanilla/api/event/entity/MCEntityStruckByLightningEvent")
@NativeTypeRegistration(value = EntityStruckByLightningEvent.class, zenCodeName = "crafttweaker.api.event.entity.MCEntityStruckByLightningEvent")
public class ExpandEntityStruckByLightningEvent {

}
