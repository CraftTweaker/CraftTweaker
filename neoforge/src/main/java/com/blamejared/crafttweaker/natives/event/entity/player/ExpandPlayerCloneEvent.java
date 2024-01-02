package com.blamejared.crafttweaker.natives.event.entity.player;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/entity/player/PlayerCloneEvent")
@NativeTypeRegistration(value = PlayerEvent.Clone.class, zenCodeName = "crafttweaker.neoforge.api.event.entity.player.PlayerCloneEvent")
public class ExpandPlayerCloneEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<PlayerEvent.Clone> BUS = IEventBus.direct(
            PlayerEvent.Clone.class,
            NeoForgeEventBusWire.of()
    );
    
    @ZenCodeType.Getter("original")
    public static Player getOriginal(PlayerEvent.Clone internal) {
        
        return internal.getOriginal();
    }
    
    @ZenCodeType.Getter("wasDeath")
    public static boolean isWasDeath(PlayerEvent.Clone internal) {
        
        return internal.isWasDeath();
    }
    
}
