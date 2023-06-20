package com.blamejared.crafttweaker.natives.event.item;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("forge/api/event/item/ItemPickupEvent")
@NativeTypeRegistration(value = PlayerEvent.ItemPickupEvent.class, zenCodeName = "crafttweaker.forge.api.event.item.ItemPickupEvent")
public class ExpandItemPickupEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<PlayerEvent.ItemPickupEvent> BUS = IEventBus.direct(
            PlayerEvent.ItemPickupEvent.class,
            ForgeEventBusWire.of()
    );
    
    @ZenCodeType.Getter("stack")
    public static IItemStack getStack(PlayerEvent.ItemPickupEvent internal) {
        
        return IItemStack.of(internal.getStack());
    }
    
    @ZenCodeType.Getter("originalEntity")
    public static ItemEntity getOriginalEntity(PlayerEvent.ItemPickupEvent internal) {
        
        return internal.getOriginalEntity();
    }
    
}
