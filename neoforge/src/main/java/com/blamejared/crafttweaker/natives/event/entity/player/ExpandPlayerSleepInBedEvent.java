package com.blamejared.crafttweaker.natives.event.entity.player;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.player.PlayerSleepInBedEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/entity/player/PlayerSleepInBedEvent")
@NativeTypeRegistration(value = PlayerSleepInBedEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.entity.player.PlayerSleepInBedEvent")
public class ExpandPlayerSleepInBedEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<PlayerSleepInBedEvent> BUS = IEventBus.direct(
            PlayerSleepInBedEvent.class,
            NeoForgeEventBusWire.of()
    );
    
    @ZenCodeType.Getter("result")
    public static Player.BedSleepingProblem getResult(PlayerSleepInBedEvent internal) {
        
        return internal.getResultStatus();
    }
    
    @ZenCodeType.Setter("result")
    public static void setResult(PlayerSleepInBedEvent internal, Player.BedSleepingProblem result) {
        
        internal.setResult(result);
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Getter("pos")
    public static BlockPos getPos(PlayerSleepInBedEvent internal) {
        
        return internal.getPos();
    }
    
    
}
