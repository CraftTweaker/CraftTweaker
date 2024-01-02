package com.blamejared.crafttweaker.natives.event.entity.player;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/entity/player/PlayerRespawnEvent")
@NativeTypeRegistration(value = PlayerEvent.PlayerRespawnEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.entity.player.PlayerRespawnEvent")
public class ExpandPlayerRespawnEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<PlayerEvent.PlayerRespawnEvent> BUS = IEventBus.direct(
            PlayerEvent.PlayerRespawnEvent.class,
            NeoForgeEventBusWire.of()
    );
    
    @ZenCodeType.Getter("isEndConquered")
    public static boolean isEndConquered(PlayerEvent.PlayerRespawnEvent internal) {
        
        return internal.isEndConquered();
    }
    
}
