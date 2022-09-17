package com.blamejared.crafttweaker.natives.event.entity.player;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.openzen.zencode.java.ZenCodeType;


@ZenRegister
@Document("forge/api/event/entity/player/ItemPickupEvent")
@NativeTypeRegistration(value = PlayerEvent.ItemPickupEvent.class, zenCodeName = "crafttweaker.api.event.entity.player.ItemPickupEvent")
public class ExpandItemPickupEvent {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("stack")
    public static IItemStack getStack(PlayerEvent.ItemPickupEvent internal) {
        
        return IItemStack.of(internal.getStack());
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("originalEntity")
    public static ItemEntity getOriginalEntity(PlayerEvent.ItemPickupEvent internal) {
        
        return internal.getOriginalEntity();
    }
    
}
