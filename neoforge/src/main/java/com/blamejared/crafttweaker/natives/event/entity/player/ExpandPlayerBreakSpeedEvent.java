package com.blamejared.crafttweaker.natives.event.entity.player;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.NeoForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/entity/player/PlayerBreakSpeedEvent")
@NativeTypeRegistration(value = PlayerEvent.BreakSpeed.class, zenCodeName = "crafttweaker.neoforge.api.event.entity.player.PlayerBreakSpeedEvent")
public class ExpandPlayerBreakSpeedEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<PlayerEvent.BreakSpeed> BUS = IEventBus.cancelable(
            PlayerEvent.BreakSpeed.class,
            NeoForgeEventBusWire.of(),
            NeoForgeEventCancellationCarrier.of()
    );
    
    @ZenCodeType.Getter("state")
    public static BlockState getState(PlayerEvent.BreakSpeed internal) {
        
        return internal.getState();
    }
    
    @ZenCodeType.Getter("originalSpeed")
    public static float getOriginalSpeed(PlayerEvent.BreakSpeed internal) {
        
        return internal.getOriginalSpeed();
    }
    
    @ZenCodeType.Getter("newSpeed")
    public static float getNewSpeed(PlayerEvent.BreakSpeed internal) {
        
        return internal.getNewSpeed();
    }
    
    @ZenCodeType.Setter("newSpeed")
    public static void setNewSpeed(PlayerEvent.BreakSpeed internal, float newSpeed) {
        
        internal.setNewSpeed(newSpeed);
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Getter("position")
    public static BlockPos getPosition(PlayerEvent.BreakSpeed internal) {
        
        return internal.getPosition().orElse(null);
    }
    
}
