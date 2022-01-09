package com.blamejared.crafttweaker.natives.event.entity.living.teleport;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.EntityTeleportEvent;

/**
 * SpreadPlayersCommandTeleportEvent is fired before a living entity is teleported by the teleport command.
 *
 * @docEvent canceled the teleport won't happen.
 */
@ZenRegister
@Document("forge/api/event/entity/living/teleport/TeleportCommandTeleportEvent")
@NativeTypeRegistration(value = EntityTeleportEvent.TeleportCommand.class, zenCodeName = "crafttweaker.api.event.living.teleport.TeleportCommandTeleportEvent")
public class ExpandTeleportCommandTeleportEvent {

}
