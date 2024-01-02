package com.blamejared.crafttweaker.natives.event.item;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.Container;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("neoforge/api/event/item/ItemCraftedEvent")
@NativeTypeRegistration(value = PlayerEvent.ItemCraftedEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.item.ItemCraftedEvent")
public class ExpandItemCraftedEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<PlayerEvent.ItemCraftedEvent> BUS = IEventBus.direct(
            PlayerEvent.ItemCraftedEvent.class,
            NeoForgeEventBusWire.of()
    );
    
    @ZenCodeType.Getter("crafting")
    public static IItemStack getCrafting(PlayerEvent.ItemCraftedEvent internal) {
        
        return IItemStack.of(internal.getCrafting());
    }
    
    @ZenCodeType.Getter("inventory")
    public static Container getInventory(PlayerEvent.ItemCraftedEvent internal) {
        
        return internal.getInventory();
    }
    
}
