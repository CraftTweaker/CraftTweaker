package com.blamejared.crafttweaker.natives.event.block;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.level.BlockEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Fired when a fluid places a block. This can happen on one of two scenarios:
 * 1) When fluids touch each other, spawning a block (When Lava and Water touch they create Cobblestone).
 * 2) When Lava spawns fire around it.
 *
 * You can use this event to change what block is set, so you could replace cobblestone with something else.
 */
@ZenRegister
@Document("forge/api/event/block/FluidPlaceBlockEvent")
@NativeTypeRegistration(value = BlockEvent.FluidPlaceBlockEvent.class, zenCodeName = "crafttweaker.api.event.block.FluidPlaceBlockEvent")
public class ExpandFluidPlaceBlockEvent {
    
    /**
     * Gets the position of the Fluid that fired this event.
     *
     * @return The position of the Fluid.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("fluidPos")
    public static BlockPos getFluidPos(BlockEvent.FluidPlaceBlockEvent internal) {
        
        return internal.getLiquidPos();
    }
    
    /**
     * Gets the new BlockState that will be placed.
     *
     * @return The new BlockState that will be placed.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("newState")
    public static BlockState getNewState(BlockEvent.FluidPlaceBlockEvent internal) {
        
        return internal.getNewState();
    }
    
    /**
     * Sets the new BlockState that will be placed.
     *
     * @param state The new BlockState.
     *
     * @docParam state <blockstate:minecraft:dirt>
     */
    @ZenCodeType.Method
    @ZenCodeType.Setter("newState")
    public static void setNewState(BlockEvent.FluidPlaceBlockEvent internal, BlockState state) {
        
        internal.setNewState(state);
    }
    
    /**
     * Gets the original BlockState in the world before the event was fired.
     *
     * @return The original BlockState in the world before the event was fired.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("originalState")
    public static BlockState getOriginalState(BlockEvent.FluidPlaceBlockEvent internal) {
        
        return internal.getOriginalState();
    }
    
}
