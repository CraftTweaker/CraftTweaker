package com.blamejared.crafttweaker.natives.event.tick;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.TickEvent;

@ZenRegister
@Document("vanilla/api/event/tick/Phase")
@NativeTypeRegistration(value = TickEvent.Phase.class, zenCodeName = "crafttweaker.api.event.tick.Phase")
@BracketEnum("forge:event/tick/phase")
public class ExpandTickEventPhase {

}
