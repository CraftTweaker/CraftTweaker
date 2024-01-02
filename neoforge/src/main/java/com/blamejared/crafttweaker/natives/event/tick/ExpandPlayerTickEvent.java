package com.blamejared.crafttweaker.natives.event.tick;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.TickEvent;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Consumer;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/tick/PlayerTickEvent")
@NativeTypeRegistration(value = TickEvent.PlayerTickEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.tick.PlayerTickEvent")
public class ExpandPlayerTickEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<TickEvent.PlayerTickEvent> BUS = IEventBus.direct(
            TickEvent.PlayerTickEvent.class,
            NeoForgeEventBusWire.of()
    );
    
    @ZenCodeType.Getter("player")
    public static Player getPlayer(TickEvent.PlayerTickEvent internal) {
        
        return internal.player;
    }
    
    @ZenCodeType.Method
    public static void every(TickEvent.PlayerTickEvent internal, int ticks, Consumer<TickEvent.PlayerTickEvent> event) {
        
        if(internal.player.level().getGameTime() % ticks == 0) {
            event.accept(internal);
        }
    }
    
}
