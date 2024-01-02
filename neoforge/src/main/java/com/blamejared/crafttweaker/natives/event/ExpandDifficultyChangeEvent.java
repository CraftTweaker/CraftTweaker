package com.blamejared.crafttweaker.natives.event;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.Difficulty;
import net.neoforged.neoforge.event.DifficultyChangeEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/DifficultyChangeEvent")
@NativeTypeRegistration(value = DifficultyChangeEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.DifficultyChangeEvent")
public class ExpandDifficultyChangeEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<DifficultyChangeEvent> BUS = IEventBus.direct(
            DifficultyChangeEvent.class,
            NeoForgeEventBusWire.of()
    );
    
    @ZenCodeType.Getter("difficulty")
    public static Difficulty getDifficulty(DifficultyChangeEvent internal) {
        
        return internal.getDifficulty();
    }
    
    @ZenCodeType.Getter("oldDifficulty")
    public static Difficulty getOldDifficulty(DifficultyChangeEvent internal) {
        
        return internal.getOldDifficulty();
    }
    
}
