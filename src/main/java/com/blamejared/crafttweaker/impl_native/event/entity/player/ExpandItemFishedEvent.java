package com.blamejared.crafttweaker.impl_native.event.entity.player;

import java.util.List;

import org.openzen.zencode.java.ZenCodeType;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemFishedEvent;

/**
 * This event is fired every time the player fishes up an item. It can be used
 * to add or remove drops, change the durability damage, do other effects, and
 * even prevent the fishing by canceling the event.
 * 
 * @docParam this event
 */
@ZenRegister
@Document("vanilla/api/event/entity/player/MCItemFishedEvent")
@NativeTypeRegistration(value = ItemFishedEvent.class, zenCodeName = "crafttweaker.api.event.entity.player.MCItemFishedEvent")
public class ExpandItemFishedEvent {
    
	/**
	 * Gets the list of items being fished up by the player.
	 * 
	 * @return The list of items being fished up by the player.
	 */
    @ZenCodeType.Method
    @ZenCodeType.Getter("drops")
    public static List<ItemStack> getDrops(ItemFishedEvent internal) {
        return internal.getDrops();
    }
    
    /**
     * Gets the amount of durability damage to inflict on the fishing rod.
     * 
     * @return The amount of durability damage to inflict on the fishing rod.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("itemDamage")
    public static int getItemDamage(ItemFishedEvent internal) {
        return internal.getRodDamage();
    }
    
    /**
     * Sets the amount of durability damage to inflict on the fishing rod.
     * 
     * @param damage The amount of durability damage.
     * 
     * @docParam damage 5
     */
    @ZenCodeType.Method
    @ZenCodeType.Setter("itemDamage")
    public static void setItemDamage(ItemFishedEvent internal, int damage) {
        internal.damageRodBy(damage);
    }
}