package com.blamejared.crafttweaker.natives.event.entity.item;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Event that is fired when an EntityItem's age has reached its maximum lifespan.
 *
 * @docEvent canceled will prevent the EntityItem from being flagged as dead, thus staying it's removal from the world. If canceled it will add more time to the entities life equal to extraLife.
 */
@ZenRegister
@Document("forge/api/event/entity/item/ItemExpireEvent")
@NativeTypeRegistration(value = ItemExpireEvent.class, zenCodeName = "crafttweaker.api.event.entity.item.ItemExpireEvent")
public class ExpandItemExpireEvent {
    
    @ZenCodeType.Getter("extraLife")
    public static int getExtraLife(ItemExpireEvent internal) {
        
        return internal.getExtraLife();
    }
    
    @ZenCodeType.Setter("extraLife")
    public static void setExtraLife(ItemExpireEvent internal, int value) {
        
        internal.setExtraLife(value);
    }
    
}
