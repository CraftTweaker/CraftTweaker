package com.blamejared.crafttweaker.natives.event.entity.player;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("forge/api/event/entity/player/PlayerWakeUpEvent")
@NativeTypeRegistration(value = PlayerWakeUpEvent.class, zenCodeName = "crafttweaker.forge.api.event.entity.player.PlayerWakeUpEvent")
public class ExpandPlayerWakeUpEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<PlayerWakeUpEvent> BUS = IEventBus.direct(
            PlayerWakeUpEvent.class,
            ForgeEventBusWire.of()
    );
    
    @ZenCodeType.Getter("wakeImmediately")
    public static boolean wakeImmediately(PlayerWakeUpEvent internal) {
        
        return internal.wakeImmediately();
    }
    
    @ZenCodeType.Getter("updateLevel")
    public static boolean updateLevel(PlayerWakeUpEvent internal) {
        
        return internal.updateLevel();
    }
    
}
