package com.blamejared.crafttweaker.natives.event.xp;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.neoforged.neoforge.event.entity.player.PlayerXpEvent;

@ZenRegister
@Document("neoforge/api/event/xp/PlayerXpEvent")
@NativeTypeRegistration(value = PlayerXpEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.xp.PlayerXpEvent")
public class ExpandPlayerXpEvent {
    

}
