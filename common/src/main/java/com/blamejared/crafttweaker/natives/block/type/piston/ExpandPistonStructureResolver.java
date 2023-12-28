package com.blamejared.crafttweaker.natives.block.type.piston;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.piston.PistonStructureResolver;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;

@ZenRegister
@Document("vanilla/api/block/type/piston/PistonStructureResolver")
@NativeTypeRegistration(value = PistonStructureResolver.class, zenCodeName = "crafttweaker.api.block.type.piston.PistonStructureResolver")
public class ExpandPistonStructureResolver {
    
    /**
     * Calculates and caches the blocks to push and the blocks to destroy.
     *
     * @return True if blocks should move, false otherwise
     */
    @ZenCodeType.Method
    public static boolean resolve(PistonStructureResolver internal) {
        
        return internal.resolve();
    }
    
    /**
     * Gets the direction that the piston is moving.
     *
     * <p>This is usually the direction that the piston block is facing, however if the piston is retracting, the direction is the opposite.</p>
     *
     * @return The direction that the piston is moving.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("pushDirection")
    public static Direction getPushDirection(PistonStructureResolver internal) {
        
        return internal.getPushDirection();
    }
    
    /**
     * Gets the blocks that will be pushed.
     *
     * @return The blocks that will be pushed.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("toPush")
    public static List<BlockPos> getToPush(PistonStructureResolver internal) {
        
        return internal.getToPush();
    }
    
    /**
     * Gets the blocks that will be destroyed by pushing the piston.
     *
     * @return The blocks that will be destroyed.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("toDestroy")
    public static List<BlockPos> getToDestroy(PistonStructureResolver internal) {
        
        return internal.getToDestroy();
    }
    
}
