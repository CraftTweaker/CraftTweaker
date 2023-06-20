package com.blamejared.crafttweaker.natives.event.item;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("forge/api/event/item/ItemSmeltedEvent")
@NativeTypeRegistration(value = PlayerEvent.ItemSmeltedEvent.class, zenCodeName = "crafttweaker.forge.api.event.item.ItemSmeltedEvent")
public class ExpandItemSmeltedEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<PlayerEvent.ItemSmeltedEvent> BUS = IEventBus.direct(
            PlayerEvent.ItemSmeltedEvent.class,
            ForgeEventBusWire.of()
    );
    
    @ZenCodeType.Getter("smelting")
    public static IItemStack getSmelting(PlayerEvent.ItemSmeltedEvent internal) {
        
        return IItemStack.of(internal.getSmelting());
    }
    
}
