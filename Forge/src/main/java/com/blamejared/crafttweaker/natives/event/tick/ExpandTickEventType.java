package com.blamejared.crafttweaker.natives.event.tick;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.TickEvent;

@ZenRegister
@Document("forge/api/event/tick/Type")
@NativeTypeRegistration(value = TickEvent.Type.class, zenCodeName = "crafttweaker.api.event.tick.Type")
@BracketEnum("forge:event/tick/type")
public class ExpandTickEventType {

}
