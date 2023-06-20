package com.blamejared.crafttweaker.natives.event.block.fluid;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.level.BlockEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("forge/api/event/block/fluid/FluidPlaceBlockEvent")
@NativeTypeRegistration(value = BlockEvent.FluidPlaceBlockEvent.class, zenCodeName = "crafttweaker.forge.api.event.block.fluid.FluidPlaceBlockEvent")
public class ExpandFluidPlaceBlockEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<BlockEvent.FluidPlaceBlockEvent> BUS = IEventBus.cancelable(
            BlockEvent.FluidPlaceBlockEvent.class,
            ForgeEventBusWire.of(),
            ForgeEventCancellationCarrier.of()
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
