package com.blamejared.crafttweaker.natives.event.entity.player;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.player.PlayerFlyableFallEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("forge/api/event/entity/player/PlayerFlyableFallEvent")
@NativeTypeRegistration(value = PlayerFlyableFallEvent.class, zenCodeName = "crafttweaker.forge.api.event.entity.player.PlayerFlyableFallEvent")
public class ExpandPlayerFlyableFallEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<PlayerFlyableFallEvent> BUS = IEventBus.direct(
            PlayerFlyableFallEvent.class,
            ForgeEventBusWire.of()
    );
    
    @ZenCodeType.Getter("distance")
    public static float getDistance(PlayerFlyableFallEvent internal) {
        
        return internal.getDistance();
    }
    
    @ZenCodeType.Setter("distance")
    public static void setDistance(PlayerFlyableFallEvent internal, float distance) {
        
        internal.setDistance(distance);
    }
    
    @ZenCodeType.Getter("multiplier")
    public static float getMultiplier(PlayerFlyableFallEvent internal) {
        
        return internal.getMultiplier();
    }
    
    @ZenCodeType.Setter("multiplier")
    public static void setMultiplier(PlayerFlyableFallEvent internal, float multiplier) {
        
        internal.setMultiplier(multiplier);
    }
    
}
