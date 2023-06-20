package com.blamejared.crafttweaker.natives.event.xp;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.player.PlayerXpEvent;

@ZenRegister
@Document("forge/api/event/xp/PlayerXpEvent")
@NativeTypeRegistration(value = PlayerXpEvent.class, zenCodeName = "crafttweaker.forge.api.event.xp.PlayerXpEvent")
public class ExpandPlayerXpEvent {
    

}
