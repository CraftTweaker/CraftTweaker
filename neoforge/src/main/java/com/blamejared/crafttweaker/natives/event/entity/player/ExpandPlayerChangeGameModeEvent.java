package com.blamejared.crafttweaker.natives.event.entity.player;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.NeoForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.GameType;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/entity/player/PlayerChangeGameModeEvent")
@NativeTypeRegistration(value = PlayerEvent.PlayerChangeGameModeEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.entity.player.PlayerChangeGameModeEvent")
public class ExpandPlayerChangeGameModeEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<PlayerEvent.PlayerChangeGameModeEvent> BUS = IEventBus.cancelable(
            PlayerEvent.PlayerChangeGameModeEvent.class,
            NeoForgeEventBusWire.of(),
            NeoForgeEventCancellationCarrier.of()
    );
    
    @ZenCodeType.Getter("currentGameMode")
    public static GameType getCurrentGameMode(PlayerEvent.PlayerChangeGameModeEvent internal) {
        
        return internal.getCurrentGameMode();
    }
    
    @ZenCodeType.Getter("newGameMode")
    public static GameType getNewGameMode(PlayerEvent.PlayerChangeGameModeEvent internal) {
        
        return internal.getNewGameMode();
    }
    
    @ZenCodeType.Setter("newGameMode")
    public static void setNewGameMode(PlayerEvent.PlayerChangeGameModeEvent internal, GameType newGameMode) {
        
        internal.setNewGameMode(newGameMode);
    }
    
}
