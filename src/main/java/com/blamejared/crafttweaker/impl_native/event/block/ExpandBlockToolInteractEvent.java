package com.blamejared.crafttweaker.impl_native.event.block;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.world.BlockEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Fired when when this block is right clicked by a tool to change its state.
 * For example: Used to determine if an axe can strip, a shovel can path, or a hoe can till.
 *
 * @docEvent cancelled this will prevent the tool from changing the block's state.
 */
@ZenRegister
@Document("vanilla/api/event/block/MCBlockToolInteractEvent")
@NativeTypeRegistration(value = BlockEvent.BlockToolInteractEvent.class, zenCodeName = "crafttweaker.api.event.block.MCBlockToolInteractEvent")
public class ExpandBlockToolInteractEvent {
    
    @ZenCodeType.Getter("player")
    @ZenCodeType.Method
    public static PlayerEntity getPlayer(BlockEvent.BlockToolInteractEvent internal) {
        
        return internal.getPlayer();
    }
    
    @ZenCodeType.Getter("heldItemStack")
    @ZenCodeType.Method
    public static IItemStack getHeldItemStack(BlockEvent.BlockToolInteractEvent internal) {
        
        return new MCItemStack(internal.getHeldItemStack());
    }
    
    @ZenCodeType.Getter("toolType")
    @ZenCodeType.Method
    public static String getToolType(BlockEvent.BlockToolInteractEvent internal) {
        
        return internal.getToolType().getName();
    }
    
    /**
     * Gets the transformed state after tool use.
     * If setFinalState not called, will return the original state.
     * This will be bypassed if canceled returning null instead.
     */
    @ZenCodeType.Getter("finalState")
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    public static BlockState getFinalState(BlockEvent.BlockToolInteractEvent internal) {
        
        return internal.getFinalState();
    }
    
    /**
     * Sets the transformed state after tool use.
     * If not set, will return the original state.
     * This will be bypassed if canceled returning null instead.
     */
    @ZenCodeType.Setter("finalState")
    @ZenCodeType.Method
    public static void setFinalState(BlockEvent.BlockToolInteractEvent internal, BlockState state) {
        
        internal.setFinalState(state);
    }
    
}
