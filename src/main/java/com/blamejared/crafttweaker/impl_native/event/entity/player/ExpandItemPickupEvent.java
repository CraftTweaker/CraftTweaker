package com.blamejared.crafttweaker.impl_native.event.entity.player;


import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.openzen.zencode.java.ZenCodeType;


@ZenRegister
@Document("vanilla/api/event/entity/player/MCItemPickupEvent")
@NativeTypeRegistration(value = PlayerEvent.ItemPickupEvent.class, zenCodeName = "crafttweaker.api.event.entity.player.MCItemPickupEvent")
public class ExpandItemPickupEvent {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("stack")
    public static IItemStack getStack(PlayerEvent.ItemPickupEvent internal) {
        return new MCItemStack(internal.getStack());
    }
}
