package com.blamejared.crafttweaker.natives.event.block;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.level.BlockEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("forge/api/event/block/FarmlandTrampleEvent")
@NativeTypeRegistration(value = BlockEvent.FarmlandTrampleEvent.class, zenCodeName = "crafttweaker.forge.api.event.block.FarmlandTrampleEvent")
public class ExpandFarmlandTrampleEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<BlockEvent.FarmlandTrampleEvent> BUS = IEventBus.cancelable(
            BlockEvent.FarmlandTrampleEvent.class,
            ForgeEventBusWire.of(),
            ForgeEventCancellationCarrier.of()
    );
    
    @ZenCodeType.Getter("entity")
    public static Entity getEntity(BlockEvent.FarmlandTrampleEvent internal) {
        
        return internal.getEntity();
    }
    
    @ZenCodeType.Getter("fallDistance")
    public static float getFallDistance(BlockEvent.FarmlandTrampleEvent internal) {
        
        return internal.getFallDistance();
    }
    
}
