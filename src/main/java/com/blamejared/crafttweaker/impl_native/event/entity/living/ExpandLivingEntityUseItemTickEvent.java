package com.blamejared.crafttweaker.impl_native.event.entity.living;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.EventCancelable;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;

/**
 * Fired every tick that a player is 'using' an item, see {@link LivingEntityUseItemEvent.Start} for info.
 *
 * Cancel the event, or set the duration or <= 0 to cause the player to stop using the item.
 */
@ZenRegister
@Document("vanilla/api/event/entity/living/MCLivingEntityUseItemTickEvent")
@EventCancelable
@NativeTypeRegistration(value = LivingEntityUseItemEvent.Tick.class, zenCodeName = "crafttweaker.api.event.entity.living.MCLivingEntityUseItemTickEvent")
public class ExpandLivingEntityUseItemTickEvent {
}
