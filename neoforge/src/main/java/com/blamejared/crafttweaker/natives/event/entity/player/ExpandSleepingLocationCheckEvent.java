package com.blamejared.crafttweaker.natives.event.entity.player;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.BlockPos;
import net.neoforged.neoforge.event.entity.player.SleepingLocationCheckEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/entity/player/SleepingLocationCheckEvent")
@NativeTypeRegistration(value = SleepingLocationCheckEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.entity.player.SleepingLocationCheckEvent")
public class ExpandSleepingLocationCheckEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<SleepingLocationCheckEvent> BUS = IEventBus.direct(
            SleepingLocationCheckEvent.class,
            NeoForgeEventBusWire.of()
    );
    
    
    @ZenCodeType.Getter("sleepingLocation")
    public static BlockPos getSleepingLocation(SleepingLocationCheckEvent internal) {
        
        return internal.getSleepingLocation();
    }
    
}
