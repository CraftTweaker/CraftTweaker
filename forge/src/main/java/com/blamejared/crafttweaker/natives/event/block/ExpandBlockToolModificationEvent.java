package com.blamejared.crafttweaker.natives.event.block;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.event.level.BlockEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("forge/api/event/block/BlockToolModificationEvent")
@NativeTypeRegistration(value = BlockEvent.BlockToolModificationEvent.class, zenCodeName = "crafttweaker.forge.api.event.block.BlockToolModificationEvent")
public class ExpandBlockToolModificationEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<BlockEvent.BlockToolModificationEvent> BUS = IEventBus.cancelable(
            BlockEvent.BlockToolModificationEvent.class,
            ForgeEventBusWire.of(),
            ForgeEventCancellationCarrier.of()
    );
    
    @ZenCodeType.Getter("player")
    public static Player getPlayer(BlockEvent.BlockToolModificationEvent internal) {
        
        return internal.getPlayer();
    }
    
    @ZenCodeType.Getter("heldItemStack")
    public static IItemStack getHeldItemStack(BlockEvent.BlockToolModificationEvent internal) {
        
        return IItemStack.of(internal.getHeldItemStack());
    }
    
    @ZenCodeType.Getter("toolAction")
    public static ToolAction getToolAction(BlockEvent.BlockToolModificationEvent internal) {
        
        return internal.getToolAction();
    }
    
    @ZenCodeType.Getter("isSimulated")
    public static boolean isSimulated(BlockEvent.BlockToolModificationEvent internal) {
        
        return internal.isSimulated();
    }
    
    @ZenCodeType.Getter("context")
    public static UseOnContext getContext(BlockEvent.BlockToolModificationEvent internal) {
        
        return internal.getContext();
    }
    
    /**
     * Sets the transformed state after tool use.
     * If not set, will return the original state.
     * This will be bypassed if canceled, returning null instead.
     */
    @ZenCodeType.Setter("finalState")
    public static void setFinalState(BlockEvent.BlockToolModificationEvent internal, BlockState state) {
        
        internal.setFinalState(state);
    }
    
    /**
     * Gets the transformed state after tool use.
     * If setFinalState is not called, it will return the original state.
     * This will be bypassed if canceled, returning null instead.
     */
    @ZenCodeType.Getter("finalState")
    @ZenCodeType.Nullable
    public static BlockState getFinalState(BlockEvent.BlockToolModificationEvent internal) {
        
        return internal.getFinalState();
    }
    
}
