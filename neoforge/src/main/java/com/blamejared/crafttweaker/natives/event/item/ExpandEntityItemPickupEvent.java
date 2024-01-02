package com.blamejared.crafttweaker.natives.event.item;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.NeoForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.item.ItemEntity;
import net.neoforged.neoforge.event.entity.player.EntityItemPickupEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/item/EntityItemPickupEvent")
@NativeTypeRegistration(value = EntityItemPickupEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.item.EntityItemPickupEvent")
public class ExpandEntityItemPickupEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<EntityItemPickupEvent> BUS = IEventBus.cancelable(
            EntityItemPickupEvent.class,
            NeoForgeEventBusWire.of(),
            NeoForgeEventCancellationCarrier.of()
    );
    
    @ZenCodeType.Getter("item")
    public static ItemEntity getItem(EntityItemPickupEvent internal) {
        
        return internal.getItem();
    }
    
}
