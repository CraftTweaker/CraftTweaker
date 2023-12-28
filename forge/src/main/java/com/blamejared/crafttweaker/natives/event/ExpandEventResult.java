package com.blamejared.crafttweaker.natives.event;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.eventbus.api.Event;

@ZenRegister
@Document("forge/api/event/EventResult")
@NativeTypeRegistration(value = Event.Result.class, zenCodeName = "crafttweaker.forge.api.event.EventResult")
@BracketEnum("forge:event/result")
public class ExpandEventResult {

}
