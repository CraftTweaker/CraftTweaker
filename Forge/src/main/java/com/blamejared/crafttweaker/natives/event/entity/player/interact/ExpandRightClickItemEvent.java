package com.blamejared.crafttweaker.natives.event.entity.player.interact;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

/**
 * The rightClickItem event is fired whenever the player right clicks with an item in their hand.
 * It does not offer any special getters, but you can still access all members from {@link PlayerInteractEvent}
 *
 * @docEvent canceled Item#onItemRightClick will not be called
 */
@ZenRegister
@Document("forge/api/event/entity/player/interact/RightClickItemEvent")
@NativeTypeRegistration(value = PlayerInteractEvent.RightClickItem.class, zenCodeName = "crafttweaker.api.event.entity.player.interact.RightClickItemEvent")
public class ExpandRightClickItemEvent {
}
