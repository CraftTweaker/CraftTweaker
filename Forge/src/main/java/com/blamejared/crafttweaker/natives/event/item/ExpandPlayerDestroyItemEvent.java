package com.blamejared.crafttweaker.natives.event.item;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenEvent
@Document("forge/api/event/item/PlayerDestroyItemEvent")
@NativeTypeRegistration(value = PlayerDestroyItemEvent.class, zenCodeName = "crafttweaker.forge.api.event.item.PlayerDestroyItemEvent")
public class ExpandPlayerDestroyItemEvent {
    
    @ZenEvent.Bus
    public static final IEventBus<PlayerDestroyItemEvent> BUS = IEventBus.direct(
            PlayerDestroyItemEvent.class,
            ForgeEventBusWire.of()
    );
    
    @ZenCodeType.Getter("original")
    public static IItemStack getOriginal(PlayerDestroyItemEvent internal) {
        
        return IItemStack.of(internal.getOriginal());
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Getter("hand")
    public static InteractionHand getHand(PlayerDestroyItemEvent internal) {
        
        return internal.getHand();
    }
    
}
