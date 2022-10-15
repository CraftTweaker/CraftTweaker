package com.blamejared.crafttweaker.natives.event.block;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.event.world.BlockEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Fired when this block is right clicked by a tool to change its state.
 * For example: Used to determine if an axe can strip a log, a shovel can turn grass into a path, or a hoe can till dirt into farmland.
 *
 * @docEvent canceled this will prevent the tool from changing the block's state.
 */
@ZenRegister
@Document("forge/api/event/block/BlockToolModificationEvent")
@NativeTypeRegistration(value = BlockEvent.BlockToolModificationEvent.class, zenCodeName = "crafttweaker.api.event.block.BlockToolModificationEvent")
public class ExpandBlockToolModificationEvent {
    
    @ZenCodeType.Getter("player")
    @ZenCodeType.Method
    public static Player getPlayer(BlockEvent.BlockToolModificationEvent internal) {
        
        return internal.getPlayer();
    }
    
    @ZenCodeType.Getter("heldItemStack")
    @ZenCodeType.Method
    public static IItemStack getHeldItemStack(BlockEvent.BlockToolModificationEvent internal) {
        
        return Services.PLATFORM.createMCItemStack(internal.getHeldItemStack());
    }
    
    @ZenCodeType.Getter("toolAction")
    @ZenCodeType.Method
    public static ToolAction getToolType(BlockEvent.BlockToolModificationEvent internal) {
        
        return internal.getToolAction();
    }
    
    /**
     * Gets the transformed state after tool use.
     * If setFinalState is not called, it will return the original state.
     * This will be bypassed if canceled, returning null instead.
     */
    @ZenCodeType.Getter("finalState")
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    public static BlockState getFinalState(BlockEvent.BlockToolModificationEvent internal) {
        
        return internal.getFinalState();
    }
    
    /**
     * Sets the transformed state after tool use.
     * If not set, will return the original state.
     * This will be bypassed if canceled, returning null instead.
     */
    @ZenCodeType.Setter("finalState")
    @ZenCodeType.Method
    public static void setFinalState(BlockEvent.BlockToolModificationEvent internal, BlockState state) {
        
        internal.setFinalState(state);
    }
    
    @ZenCodeType.Getter("simulated")
    @ZenCodeType.Method
    public static boolean isSimulated(BlockEvent.BlockToolModificationEvent internal) {
        
        return internal.isSimulated();
    }
    
}
