package com.blamejared.crafttweaker.natives.event.entity.player;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.NeoForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.event.entity.player.PlayerSetSpawnEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/entity/player/PlayerSetSpawnEvent")
@NativeTypeRegistration(value = PlayerSetSpawnEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.entity.player.PlayerSetSpawnEvent")
public class ExpandPlayerSetSpawnEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<PlayerSetSpawnEvent> BUS = IEventBus.cancelable(
            PlayerSetSpawnEvent.class,
            NeoForgeEventBusWire.of(),
            NeoForgeEventCancellationCarrier.of()
    );
    
    @ZenCodeType.Getter("isForced")
    public static boolean isForced(PlayerSetSpawnEvent internal) {
        
        return internal.isForced();
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Getter("newSpawn")
    public static BlockPos getNewSpawn(PlayerSetSpawnEvent internal) {
        
        return internal.getNewSpawn();
    }
    
    @ZenCodeType.Getter("spawnLevel")
    public static ResourceLocation getSpawnLevel(PlayerSetSpawnEvent internal) {
        
        return internal.getSpawnLevel().location();
    }
    
}
