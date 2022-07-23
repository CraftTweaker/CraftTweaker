package com.blamejared.crafttweaker.natives.event.block.piston;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.world.PistonEvent;

/**
 * Fires before the piston has updated block states.
 *
 * @docEvent canceled prevents any movement.
 */
@ZenRegister
@Document("forge/api/event/block/piston/PistonPreEvent")
@NativeTypeRegistration(value = PistonEvent.Pre.class, zenCodeName = "crafttweaker.api.event.block.piston.PistonPreEvent")
public class ExpandPistonEventPre {
    
}
