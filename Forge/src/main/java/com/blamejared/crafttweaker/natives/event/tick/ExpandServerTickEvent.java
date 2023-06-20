package com.blamejared.crafttweaker.natives.event.tick;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.TickEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("forge/api/event/tick/ServerTickEvent")
@NativeTypeRegistration(value = TickEvent.ServerTickEvent.class, zenCodeName = "crafttweaker.forge.api.event.tick.ServerTickEvent")
public class ExpandServerTickEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<TickEvent.ServerTickEvent> BUS = IEventBus.direct(
            TickEvent.ServerTickEvent.class,
            ForgeEventBusWire.of()
    );
    
    @ZenCodeType.Getter("hasTime")
    public static boolean haveTime(TickEvent.ServerTickEvent internal) {
        
        return internal.haveTime();
    }
    
    @ZenCodeType.Getter("server")
    public static MinecraftServer getServer(TickEvent.ServerTickEvent internal) {
        
        return internal.getServer();
    }
    
}
