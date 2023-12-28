package com.blamejared.crafttweaker.natives.event.block.piston;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.level.PistonEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("forge/api/event/block/piston/PistonMoveType")
@NativeTypeRegistration(value = PistonEvent.PistonMoveType.class, zenCodeName = "crafttweaker.forge.api.event.block.piston.PistonMoveType")
@BracketEnum("forge:event/piston/move_type")
public class ExpandPistonEventMoveType {
    
    @ZenCodeType.Getter("extend")
    public static boolean isExtend(PistonEvent.PistonMoveType internal) {
        
        return internal.isExtend;
    }
    
}
