package com.blamejared.crafttweaker.natives.event.block.piston;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.world.PistonEvent;

@ZenRegister
@Document("forge/api/event/block/piston/PistonMoveType")
@NativeTypeRegistration(value = PistonEvent.PistonMoveType.class, zenCodeName = "crafttweaker.api.event.block.piston.PistonMoveType")
@BracketEnum("forge:event/piston/move_type")
public class ExpandPistonEventMoveType {
    
    // There is a public field, but like, this == <constant:forge:event/piston/move_type:extend> does the same thing.
    
}
