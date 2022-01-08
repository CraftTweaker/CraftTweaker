package com.blamejared.crafttweaker.natives.event.entity.living.use;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;

/**
 * Fired when a player starts 'using' an item, typically when they hold right mouse.
 * Examples:
 * Drawing a bow
 * Eating Food
 * Drinking Potions/Milk
 * Guarding with a sword
 *
 * Cancel the event, or set the duration or <= 0 to prevent it from processing.
 *
 * @docEvent canceled it will not process.
 */
@ZenRegister
@Document("vanilla/api/event/entity/living/use/LivingEntityUseItemStartEvent")
@NativeTypeRegistration(value = LivingEntityUseItemEvent.Start.class, zenCodeName = "crafttweaker.api.event.entity.living.use.LivingEntityUseItemStartEvent")
public class ExpandLivingEntityUseItemStartEvent {
}
