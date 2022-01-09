package com.blamejared.crafttweaker.natives.event.entity.living.use;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
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
 * @docEvent canceled will prevent the Item from being notified that it has stopped being used. The only vanilla item this would effect are bows, and it would cause them NOT to fire their arrow.
 */
@ZenRegister
@Document("forge/api/event/entity/living/use/LivingEntityUseItemStopEvent")
@NativeTypeRegistration(value = LivingEntityUseItemEvent.Stop.class, zenCodeName = "crafttweaker.api.event.entity.living.use.LivingEntityUseItemStopEvent")
public class ExpandLivingEntityUseItemStopEvent {
}
