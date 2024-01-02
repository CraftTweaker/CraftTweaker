package com.blamejared.crafttweaker.natives.event.item;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.NeoForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.item.ItemTossEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/item/ItemTossEvent")
@NativeTypeRegistration(value = ItemTossEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.item.ItemTossEvent")
public class ExpandItemTossEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<ItemTossEvent> BUS = IEventBus.cancelable(
            ItemTossEvent.class,
            NeoForgeEventBusWire.of(),
            NeoForgeEventCancellationCarrier.of()
    );
    
    @ZenCodeType.Getter("player")
    public static Player getPlayer(ItemTossEvent internal) {
        
        return internal.getPlayer();
    }
    
}
