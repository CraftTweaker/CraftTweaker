package com.blamejared.crafttweaker.natives.event.block.piston;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.piston.PistonStructureResolver;
import net.minecraftforge.event.world.PistonEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Base piston event, use {@link PistonEvent.Post} and {@link PistonEvent.Pre}
 */
@ZenRegister
@Document("forge/api/event/block/piston/PistonEvent")
@NativeTypeRegistration(value = PistonEvent.class, zenCodeName = "crafttweaker.api.event.block.piston.PistonEvent")
public class ExpandPistonEvent {
    
    /**
     * Gets the direction that the piston is facing.
     *
     * @return the direction tha the piston is facing.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("direction")
    public static Direction getDirection(PistonEvent internal) {
        
        return internal.getDirection();
    }
    
    /**
     * Gets the position that the piston is facing towards.
     *
     * @return The position that the piston is facing towards.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("faceOffsetPos")
    public static BlockPos getFaceOffsetPos(PistonEvent internal) {
        
        return internal.getFaceOffsetPos();
    }
    
    /**
     * Gets the move type of the piston (is it extending or retracting).
     *
     * @return The move type of the piston.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("pistonMoveType")
    public static PistonEvent.PistonMoveType getPistonMoveType(PistonEvent internal) {
        
        return internal.getPistonMoveType();
    }
    
    /**
     * Gets a **nullable** structure resolver that can be used to get all the blocks that will be affected by the piston.
     *
     * <p>Be sure to call the `resolve()` method on the structure resolver</p>
     *
     * @return A structure resolver.
     */
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    @ZenCodeType.Getter("structureHelper")
    public static PistonStructureResolver getStructureHelper(PistonEvent internal) {
        
        return internal.getStructureHelper();
    }
    
}
