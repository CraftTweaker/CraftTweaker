package com.blamejared.crafttweaker.natives.event.block.crop;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.level.BlockEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("forge/api/event/block/crop/CropGrowEvent")
@NativeTypeRegistration(value = BlockEvent.CropGrowEvent.class, zenCodeName = "crafttweaker.forge.api.event.block.crop.CropGrowEvent")
public class ExpandCropGrowEvent {
    

    @ZenRegister
    @ZenEvent
    @Document("forge/api/event/block/crop/PreCropGrowEvent")
    @NativeTypeRegistration(value = BlockEvent.CropGrowEvent.Pre.class, zenCodeName = "crafttweaker.forge.api.event.block.crop.PreCropGrowEvent")
    public static class ExpandCropGrowPreEvent {
        
        @ZenEvent.Bus
        public static final IEventBus<BlockEvent.CropGrowEvent.Pre> BUS = IEventBus.cancelable(
                BlockEvent.CropGrowEvent.Pre.class,
                ForgeEventBusWire.of(),
                ForgeEventCancellationCarrier.of()
        );
        
    }
    
    @ZenRegister
    @ZenEvent
    @Document("forge/api/event/block/crop/PostCropGrowEvent")
    @NativeTypeRegistration(value = BlockEvent.CropGrowEvent.Post.class, zenCodeName = "crafttweaker.forge.api.event.block.crop.PostCropGrowEvent")
    public static class ExpandCropGrowPostEvent {
        
        @ZenEvent.Bus
        public static final IEventBus<BlockEvent.CropGrowEvent.Post> BUS = IEventBus.direct(
                BlockEvent.CropGrowEvent.Post.class,
                ForgeEventBusWire.of()
        );
        
        @ZenCodeType.Getter("originalState")
        public static BlockState getOriginalState(BlockEvent.CropGrowEvent.Post internal) {
            
            return internal.getOriginalState();
        }
        
    }
    
}
