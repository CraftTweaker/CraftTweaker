package com.blamejared.crafttweaker.impl_native.event.entity.player;


import org.openzen.zencode.java.ZenCodeType;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;

import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;

/**
 * This event is fired when a player destroys a tool or item. This happens
 * after the tool has been destroyed and can not be used to prevent the
 * destruction directly.
 * 
 * @docParam this event
 */
@ZenRegister
@Document("vanilla/api/event/entity/player/MCPlayerDestroyItemEvent")
@NativeTypeRegistration(value = PlayerDestroyItemEvent.class, zenCodeName = "crafttweaker.api.event.entity.player.MCPlayerDestroyItemEvent")
public class ExpandPlayerDestroyItemEvent {
	
	/**
	 * Gets a snapshot of the item from before it broke. Modifying this item
	 * will have no effect and it should be treated as unmodifiable.
	 * 
	 * @return The original item from before it was broken.
	 */
    @ZenCodeType.Method
    @ZenCodeType.Getter("destroyedItem")
    public static IItemStack getDestroyedItem(PlayerDestroyItemEvent internal) {
        return new MCItemStack(internal.getOriginal());
    }
}