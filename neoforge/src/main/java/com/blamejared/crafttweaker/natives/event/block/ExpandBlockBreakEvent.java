package com.blamejared.crafttweaker.natives.event.block;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.NeoForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.level.BlockEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/block/BlockBreakEvent")
@NativeTypeRegistration(value = BlockEvent.BreakEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.block.BlockBreakEvent")
public class ExpandBlockBreakEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<BlockEvent.BreakEvent> BUS = IEventBus.cancelable(
            BlockEvent.BreakEvent.class,
            NeoForgeEventBusWire.of(),
            NeoForgeEventCancellationCarrier.of()
    );
    
    @ZenCodeType.Getter("player")
    public static Player getPlayer(BlockEvent.BreakEvent internal) {
        
        return internal.getPlayer();
    }
    
    @ZenCodeType.Getter("expToDrop")
    public static int getExpToDrop(BlockEvent.BreakEvent internal) {
        
        return internal.getExpToDrop();
    }
    
    @ZenCodeType.Setter("expToDrop")
    public static void setExpToDrop(BlockEvent.BreakEvent internal, int exp) {
        
        internal.setExpToDrop(exp);
    }
    
}
