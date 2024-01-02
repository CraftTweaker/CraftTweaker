package com.blamejared.crafttweaker.natives.event.level;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.NeoForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.storage.ServerLevelData;
import net.neoforged.neoforge.event.level.LevelEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/level/CreateSpawnPositionEvent")
@NativeTypeRegistration(value = LevelEvent.CreateSpawnPosition.class, zenCodeName = "crafttweaker.neoforge.api.event.level.CreateSpawnPositionEvent")
public class ExpandCreateSpawnPositionEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<LevelEvent.CreateSpawnPosition> BUS = IEventBus.cancelable(
            LevelEvent.CreateSpawnPosition.class,
            NeoForgeEventBusWire.of(),
            NeoForgeEventCancellationCarrier.of()
    );
    
    @ZenCodeType.Getter("settings")
    public static ServerLevelData getSettings(LevelEvent.CreateSpawnPosition internal) {
        
        return internal.getSettings();
    }
    
}
