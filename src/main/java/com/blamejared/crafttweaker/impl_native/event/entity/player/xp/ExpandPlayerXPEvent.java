package com.blamejared.crafttweaker.impl_native.event.entity.player.xp;


import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.player.PlayerXpEvent;

@ZenRegister
@Document("vanilla/api/event/entity/player/xp/MCPlayerXPEvent")
@NativeTypeRegistration(value = PlayerXpEvent.class, zenCodeName = "crafttweaker.api.event.entity.player.xp.MCPlayerXPEvent")
public class ExpandPlayerXPEvent {

}