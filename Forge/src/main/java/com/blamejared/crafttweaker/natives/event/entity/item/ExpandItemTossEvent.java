package com.blamejared.crafttweaker.natives.event.entity.item;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Event that is fired whenever a player tosses (Q) an item or drag-n-drops a
 * stack of items outside the inventory GUI screens.
 *
 * @docEvent canceled will stop the items from entering the world, but will not prevent them being removed from the inventory - and thus removed from the system.
 */
@ZenRegister
@Document("forge/api/event/entity/item/ItemTossEvent")
@NativeTypeRegistration(value = ItemTossEvent.class, zenCodeName = "crafttweaker.api.event.item.ItemTossEvent")
public class ExpandItemTossEvent {
    
    @ZenCodeType.Getter("player")
    public static Player getPlayer(ItemTossEvent internal) {
        
        return internal.getPlayer();
    }
    
}
