package com.blamejared.crafttweaker.natives.event.block;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.event.level.BlockEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("forge/api/event/block/EntityPlaceBlockEvent")
@NativeTypeRegistration(value = BlockEvent.EntityPlaceEvent.class, zenCodeName = "crafttweaker.forge.api.event.block.EntityPlaceBlockEvent")
public class ExpandEntityPlaceBlockEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<BlockEvent.EntityPlaceEvent> BUS = IEventBus.cancelable(
            BlockEvent.EntityPlaceEvent.class,
            ForgeEventBusWire.of(),
            ForgeEventCancellationCarrier.of()
    );
    
    @ZenCodeType.Nullable
    @ZenCodeType.Getter("entity")
    public static Entity getEntity(BlockEvent.EntityPlaceEvent internal) {
        
        return internal.getEntity();
    }
    
    //TODO expand
//    @ZenCodeType.Getter("blockSnapshot")
//    public static BlockSnapshot getBlockSnapshot(BlockEvent.EntityPlaceEvent internal) {
//
//        return internal.getBlockSnapshot();
//    }
    
    @ZenCodeType.Getter("placedBlock")
    public static BlockState getPlacedBlock(BlockEvent.EntityPlaceEvent internal) {
        
        return internal.getPlacedBlock();
    }
    
    @ZenCodeType.Getter("placedAgainst")
    public static BlockState getPlacedAgainst(BlockEvent.EntityPlaceEvent internal) {
        
        return internal.getPlacedAgainst();
    }
    
}
