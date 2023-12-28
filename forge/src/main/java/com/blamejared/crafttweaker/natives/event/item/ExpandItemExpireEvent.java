package com.blamejared.crafttweaker.natives.event.item;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ForgeEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("forge/api/event/item/ItemExpireEvent")
@NativeTypeRegistration(value = ItemExpireEvent.class, zenCodeName = "crafttweaker.forge.api.event.item.ItemExpireEvent")
public class ExpandItemExpireEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<ItemExpireEvent> BUS = IEventBus.cancelable(
            ItemExpireEvent.class,
            ForgeEventBusWire.of(),
            ForgeEventCancellationCarrier.of()
    );
    
    @ZenCodeType.Getter("extraLife")
    public static int getExtraLife(ItemExpireEvent internal) {
        
        return internal.getExtraLife();
    }
    
    @ZenCodeType.Setter("extraLife")
    public static void setExtraLife(ItemExpireEvent internal, int extraLife) {
        
        internal.setExtraLife(extraLife);
    }
    
}
