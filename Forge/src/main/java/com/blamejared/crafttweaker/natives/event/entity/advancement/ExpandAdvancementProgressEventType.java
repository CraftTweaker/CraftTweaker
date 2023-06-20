package com.blamejared.crafttweaker.natives.event.entity.advancement;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.player.AdvancementEvent;

@SuppressWarnings("deprecation")
@ZenRegister
@Document("forge/api/event/advancement/AdvancementProgressEventType")
@NativeTypeRegistration(value = AdvancementEvent.AdvancementProgressEvent.ProgressType.class, zenCodeName = "crafttweaker.forge.api.event.advancement.AdvancementProgressEventType")
@BracketEnum("forge:event/advancement/progress/type")
public class ExpandAdvancementProgressEventType {

}
