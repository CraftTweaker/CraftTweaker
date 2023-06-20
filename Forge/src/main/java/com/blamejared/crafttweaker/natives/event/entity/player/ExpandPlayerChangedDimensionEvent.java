package com.blamejared.crafttweaker.natives.event.entity.player;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("forge/api/event/entity/player/PlayerChangedDimensionEvent")
@NativeTypeRegistration(value = PlayerEvent.PlayerChangedDimensionEvent.class, zenCodeName = "crafttweaker.forge.api.event.entity.player.PlayerChangedDimensionEvent")
public class ExpandPlayerChangedDimensionEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<PlayerEvent.PlayerChangedDimensionEvent> BUS = IEventBus.direct(
            PlayerEvent.PlayerChangedDimensionEvent.class,
            ForgeEventBusWire.of()
    );
    
    @ZenCodeType.Getter("from")
    public static ResourceLocation getFrom(PlayerEvent.PlayerChangedDimensionEvent internal) {
        
        return internal.getFrom().location();
    }
    
    @ZenCodeType.Getter("to")
    public static ResourceLocation getTo(PlayerEvent.PlayerChangedDimensionEvent internal) {
        
        return internal.getTo().location();
    }
    
}
