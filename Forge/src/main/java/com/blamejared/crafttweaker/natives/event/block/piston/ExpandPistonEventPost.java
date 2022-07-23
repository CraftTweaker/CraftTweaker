package com.blamejared.crafttweaker.natives.event.block.piston;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.world.PistonEvent;

/**
 * Fires after the piston has moved and set surrounding states. This will not fire if {@link PistonEvent.Pre} is cancelled.
 */
@ZenRegister
@Document("forge/api/event/block/piston/PistonEventPost")
@NativeTypeRegistration(value = PistonEvent.Post.class, zenCodeName = "crafttweaker.api.event.block.piston.PistonEventPost")
public class ExpandPistonEventPost {
    
}
