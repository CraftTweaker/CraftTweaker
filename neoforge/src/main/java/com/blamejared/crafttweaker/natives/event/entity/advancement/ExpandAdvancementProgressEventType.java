package com.blamejared.crafttweaker.natives.event.entity.advancement;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.neoforged.neoforge.event.entity.player.AdvancementEvent;

@ZenRegister
@Document("neoforge/api/event/advancement/AdvancementProgressEventType")
@NativeTypeRegistration(value = AdvancementEvent.AdvancementProgressEvent.ProgressType.class, zenCodeName = "crafttweaker.neoforge.api.event.advancement.AdvancementProgressEventType")
@BracketEnum("neoforge:event/advancement/progress/type")
public class ExpandAdvancementProgressEventType {

}
