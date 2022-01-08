package com.blamejared.crafttweaker.natives.event;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.eventbus.api.EventPriority;

/**
 * Different priorities for Event listeners. NORMAL is the default level for a listener registered without a priority.
 *
 * HIGHEST is the first to execute, LOWEST is the last to execute.
 */
@ZenRegister
@Document("vanilla/api/event/EventPriority")
@NativeTypeRegistration(value = EventPriority.class, zenCodeName = "crafttweaker.api.events.EventPriority")
@BracketEnum("forge:event/priority")
public class ExpandEventPriority {
}
