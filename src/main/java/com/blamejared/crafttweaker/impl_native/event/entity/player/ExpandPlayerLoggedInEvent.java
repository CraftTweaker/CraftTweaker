package com.blamejared.crafttweaker.impl_native.event.entity.player;


import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.player.PlayerEvent;


/**
 * The playerLoggedIn event does not add any new Properties.
 * Since it is a {@link PlayerEvent}, you can already access the getter
 */
@ZenRegister
@Document("vanilla/api/event/entity/player/MCPlayerLoggedInEvent")
@NativeTypeRegistration(value = PlayerEvent.PlayerLoggedInEvent.class, zenCodeName = "crafttweaker.api.event.entity.player.MCPlayerLoggedInEvent")
public class ExpandPlayerLoggedInEvent {}
