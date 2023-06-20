package com.blamejared.crafttweaker.natives.event.item;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("forge/api/event/item/EntityItemPickupEvent")
@NativeTypeRegistration(value = EntityItemPickupEvent.class, zenCodeName = "crafttweaker.forge.api.event.item.EntityItemPickupEvent")
public class ExpandEntityItemPickupEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<EntityItemPickupEvent> BUS = IEventBus.cancelable(
            EntityItemPickupEvent.class,
            ForgeEventBusWire.of(),
            ForgeEventCancellationCarrier.of()
    );
    
    @ZenCodeType.Getter("item")
    public static ItemEntity getItem(EntityItemPickupEvent internal) {
        
        return internal.getItem();
    }
    
}
