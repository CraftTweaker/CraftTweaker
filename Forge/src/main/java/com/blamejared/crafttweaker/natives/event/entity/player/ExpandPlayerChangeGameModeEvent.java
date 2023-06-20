package com.blamejared.crafttweaker.natives.event.entity.player;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.GameType;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("forge/api/event/entity/player/PlayerChangeGameModeEvent")
@NativeTypeRegistration(value = PlayerEvent.PlayerChangeGameModeEvent.class, zenCodeName = "crafttweaker.forge.api.event.entity.player.PlayerChangeGameModeEvent")
public class ExpandPlayerChangeGameModeEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<PlayerEvent.PlayerChangeGameModeEvent> BUS = IEventBus.cancelable(
            PlayerEvent.PlayerChangeGameModeEvent.class,
            ForgeEventBusWire.of(),
            ForgeEventCancellationCarrier.of()
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
