package com.blamejared.crafttweaker.natives.event.tick;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.TickEvent;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Consumer;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/tick/LevelTickEvent")
@NativeTypeRegistration(value = TickEvent.LevelTickEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.tick.LevelTickEvent")
public class ExpandLevelTickEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<TickEvent.LevelTickEvent> BUS = IEventBus.direct(
            TickEvent.LevelTickEvent.class,
            NeoForgeEventBusWire.of()
    );
    
    @ZenCodeType.Getter("hasTime")
    public static boolean haveTime(TickEvent.LevelTickEvent internal) {
        
        return internal.haveTime();
    }
    
    @ZenCodeType.Getter("level")
    public static Level getLevel(TickEvent.LevelTickEvent internal) {
        
        return internal.level;
    }
    
    @ZenCodeType.Method
    public static void every(TickEvent.LevelTickEvent internal, int ticks, Consumer<TickEvent.LevelTickEvent> event) {
        
        if(internal.level.getGameTime() % ticks == 0) {
            event.accept(internal);
        }
    }
    
}
