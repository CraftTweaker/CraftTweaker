package com.blamejared.crafttweaker.impl_native.event.entity.living;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.EventCancelable;
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
 */
@ZenRegister
@Document("vanilla/api/event/entity/living/MCLivingEntityUseItemStartEvent")
@EventCancelable
@NativeTypeRegistration(value = LivingEntityUseItemEvent.Start.class, zenCodeName = "crafttweaker.api.event.entity.living.MCLivingEntityUseItemStartEvent")
public class ExpandLivingEntityUseItemStartEvent {
}
