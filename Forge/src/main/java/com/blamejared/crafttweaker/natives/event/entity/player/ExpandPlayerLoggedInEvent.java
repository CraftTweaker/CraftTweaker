package com.blamejared.crafttweaker.natives.event.entity.player;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.player.PlayerEvent;


/**
 * The playerLoggedIn event does not add any new Properties.
 * Since it is a {@link PlayerEvent}, you can already access the player getter
 */
@ZenRegister
@Document("forge/api/event/entity/player/PlayerLoggedInEvent")
@NativeTypeRegistration(value = PlayerEvent.PlayerLoggedInEvent.class, zenCodeName = "crafttweaker.api.event.entity.player.PlayerLoggedInEvent")
public class ExpandPlayerLoggedInEvent {}
