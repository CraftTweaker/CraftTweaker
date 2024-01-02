package com.blamejared.crafttweaker.natives.event.entity.player;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.NeoForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.entity.player.BonemealEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/entity/player/BonemealEvent")
@NativeTypeRegistration(value = BonemealEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.entity.player.BonemealEvent")
public class ExpandBonemealEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<BonemealEvent> BUS = IEventBus.cancelable(
            BonemealEvent.class,
            NeoForgeEventBusWire.of(),
            NeoForgeEventCancellationCarrier.of()
    );
    
    @ZenCodeType.Getter("level")
    public static Level getLevel(BonemealEvent internal) {
        
        return internal.getLevel();
    }
    
    @ZenCodeType.Getter("pos")
    public static BlockPos getPos(BonemealEvent internal) {
        
        return internal.getPos();
    }
    
    @ZenCodeType.Getter("block")
    public static BlockState getBlock(BonemealEvent internal) {
        
        return internal.getBlock();
    }
    
    @ZenCodeType.Getter("stack")
    public static IItemStack getStack(BonemealEvent internal) {
        
        return IItemStack.of(internal.getStack());
    }
    
}
