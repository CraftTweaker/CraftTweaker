package com.blamejared.crafttweaker.natives.event.xp;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("forge/api/event/xp/PlayerXpLevelChangeEvent")
@NativeTypeRegistration(value = PlayerXpEvent.LevelChange.class, zenCodeName = "crafttweaker.forge.api.event.xp.PlayerXpLevelChangeEvent")
public class ExpandPlayerXpLevelChangeEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<PlayerXpEvent.LevelChange> BUS = IEventBus.cancelable(
            PlayerXpEvent.LevelChange.class,
            ForgeEventBusWire.of(),
            ForgeEventCancellationCarrier.of()
    );
    
    @ZenCodeType.Getter("levels")
    public static int getLevels(PlayerXpEvent.LevelChange internal) {
        
        return internal.getLevels();
    }
    
    @ZenCodeType.Setter("levels")
    public static void setLevels(PlayerXpEvent.LevelChange internal, int levels) {
        
        internal.setLevels(levels);
    }
    
}
