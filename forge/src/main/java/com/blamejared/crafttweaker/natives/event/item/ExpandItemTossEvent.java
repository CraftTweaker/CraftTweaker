package com.blamejared.crafttweaker.natives.event.item;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("forge/api/event/item/ItemTossEvent")
@NativeTypeRegistration(value = ItemTossEvent.class, zenCodeName = "crafttweaker.forge.api.event.item.ItemTossEvent")
public class ExpandItemTossEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<ItemTossEvent> BUS = IEventBus.cancelable(
            ItemTossEvent.class,
            ForgeEventBusWire.of(),
            ForgeEventCancellationCarrier.of()
    );
    
    @ZenCodeType.Getter("player")
    public static Player getPlayer(ItemTossEvent internal) {
        
        return internal.getPlayer();
    }
    
}
