package com.blamejared.crafttweaker.natives.event.entity.living;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.living.LivingDestroyBlockEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("forge/api/event/entity/living/LivingDestroyBlockEvent")
@NativeTypeRegistration(value = LivingDestroyBlockEvent.class, zenCodeName = "crafttweaker.forge.api.event.entity.living.LivingDestroyBlockEvent")
public class ExpandLivingDestroyBlockEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<LivingDestroyBlockEvent> BUS = IEventBus.cancelable(
            LivingDestroyBlockEvent.class,
            ForgeEventBusWire.of(),
            ForgeEventCancellationCarrier.of()
    );
    
    @ZenCodeType.Getter("state")
    public static BlockState getState(LivingDestroyBlockEvent internal) {
        
        return internal.getState();
    }
    
    @ZenCodeType.Getter("pos")
    public static BlockPos getPos(LivingDestroyBlockEvent internal) {
        
        return internal.getPos();
    }
    
}
