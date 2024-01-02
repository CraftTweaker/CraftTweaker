package com.blamejared.crafttweaker.natives.event.block.fluid;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.NeoForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.level.BlockEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/block/fluid/FluidPlaceBlockEvent")
@NativeTypeRegistration(value = BlockEvent.FluidPlaceBlockEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.block.fluid.FluidPlaceBlockEvent")
public class ExpandFluidPlaceBlockEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<BlockEvent.FluidPlaceBlockEvent> BUS = IEventBus.cancelable(
            BlockEvent.FluidPlaceBlockEvent.class,
            NeoForgeEventBusWire.of(),
            NeoForgeEventCancellationCarrier.of()
    );
    
    @ZenCodeType.Getter("liquidPos")
    public static BlockPos getLiquidPos(BlockEvent.FluidPlaceBlockEvent internal) {
        
        return internal.getLiquidPos();
    }
    
    @ZenCodeType.Getter("newState")
    public static BlockState getNewState(BlockEvent.FluidPlaceBlockEvent internal) {
        
        return internal.getNewState();
    }
    
    @ZenCodeType.Setter("newState")
    public static void setNewState(BlockEvent.FluidPlaceBlockEvent internal, BlockState state) {
        
        internal.setNewState(state);
    }
    
    @ZenCodeType.Getter("originalState")
    public static BlockState getOriginalState(BlockEvent.FluidPlaceBlockEvent internal) {
        
        return internal.getOriginalState();
    }
    
}
