package com.blamejared.crafttweaker.natives.event.block.fluid;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.level.BlockEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/block/fluid/CreateFluidSourceEvent")
@NativeTypeRegistration(value = BlockEvent.CreateFluidSourceEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.block.fluid.CreateFluidSourceEvent")
public class ExpandCreateFluidSourceEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<BlockEvent.CreateFluidSourceEvent> BUS = IEventBus.direct(
            BlockEvent.CreateFluidSourceEvent.class,
            NeoForgeEventBusWire.of()
    );
    
    @ZenCodeType.Getter("level")
    public static Level getLevel(BlockEvent.CreateFluidSourceEvent internal) {
        
        return internal.getLevel();
    }
    
    @ZenCodeType.Getter("pos")
    public static BlockPos getPos(BlockEvent.CreateFluidSourceEvent internal) {
        
        return internal.getPos();
    }
    
    @ZenCodeType.Getter("state")
    public static BlockState getState(BlockEvent.CreateFluidSourceEvent internal) {
        
        return internal.getState();
    }
    
    
}
