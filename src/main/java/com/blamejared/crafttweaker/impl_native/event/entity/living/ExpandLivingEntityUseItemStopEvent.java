package com.blamejared.crafttweaker.impl_native.event.entity.living;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;

/**
 * Fired when a player stops using an item without the use duration timing out.
 * Example:
 * Stop eating 1/2 way through
 * Stop defending with sword
 * Stop drawing bow. This case would fire the arrow
 *
 * Duration on this event is how long the item had left in it's count down before 'finishing'
 *
 * @docEvent canceled will prevent the Item from being notified that it has stopped being used. The only vanilla item this would effect are bows, and it would cause them NOT to fire there arrow.
 */
@ZenRegister
@Document("vanilla/api/event/entity/living/MCLivingEntityUseItemStopEvent")
@NativeTypeRegistration(value = LivingEntityUseItemEvent.Stop.class, zenCodeName = "crafttweaker.api.event.entity.living.MCLivingEntityUseItemStopEvent")
public class ExpandLivingEntityUseItemStopEvent {
}
