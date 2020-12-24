package com.blamejared.crafttweaker.impl_native.event.entity.player.interact;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;


/**
 * The rightClickEmpty event is fired whenever the player right clicks with an empty hand.
 * It does not offer any special getters, but you can still access all members from {@link PlayerInteractEvent}
 */
@ZenRegister
@Document("vanilla/api/event/entity/player/interact/MCRightClickEmptyEvent")
@NativeTypeRegistration(value = PlayerInteractEvent.RightClickEmpty.class, zenCodeName = "crafttweaker.api.event.entity.player.interact.MCRightClickEmptyEvent")
public class ExpandRightClickEmptyEvent {}
