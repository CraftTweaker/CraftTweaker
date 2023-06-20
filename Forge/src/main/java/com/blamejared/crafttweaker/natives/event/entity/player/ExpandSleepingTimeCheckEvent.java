package com.blamejared.crafttweaker.natives.event.entity.player;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.BlockPos;
import net.minecraftforge.event.entity.player.SleepingTimeCheckEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("forge/api/event/entity/player/SleepingTimeCheckEvent")
@NativeTypeRegistration(value = SleepingTimeCheckEvent.class, zenCodeName = "crafttweaker.forge.api.event.entity.player.SleepingTimeCheckEvent")
public class ExpandSleepingTimeCheckEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<SleepingTimeCheckEvent> BUS = IEventBus.direct(
            SleepingTimeCheckEvent.class,
            ForgeEventBusWire.of()
    );
    
    @ZenCodeType.Getter("sleepingLocation")
    public static BlockPos getSleepingLocation(SleepingTimeCheckEvent internal) {
        
        return internal.getSleepingLocation().orElse(null);
    }
    
}
