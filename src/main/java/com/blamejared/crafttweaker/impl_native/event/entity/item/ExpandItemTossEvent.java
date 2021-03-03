package com.blamejared.crafttweaker.impl_native.event.entity.item;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Event that is fired whenever a player tosses (Q) an item or drag-n-drops a
 * stack of items outside the inventory GUI screens.
 *
 * @docEvent canceled will stop the items from entering the world, but will not prevent them being removed from the inventory - and thus removed from the system.
 */
@ZenRegister
@Document("vanilla/api/event/entity/item/MCItemTossEvent")
@NativeTypeRegistration(value = ItemTossEvent.class, zenCodeName = "crafttweaker.api.event.item.MCItemTossEvent")
public class ExpandItemTossEvent {
    
    @ZenCodeType.Getter("player")
    public static PlayerEntity getPlayer(ItemTossEvent internal) {
        
        return internal.getPlayer();
    }
    
}
