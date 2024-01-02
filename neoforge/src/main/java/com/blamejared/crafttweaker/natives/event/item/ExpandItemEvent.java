package com.blamejared.crafttweaker.natives.event.item;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.item.ItemEntity;
import net.neoforged.neoforge.event.entity.item.ItemEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("neoforge/api/event/item/ItemEvent")
@NativeTypeRegistration(value = ItemEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.item.ItemEvent")
public class ExpandItemEvent {
    
    @ZenCodeType.Getter("entity")
    public static ItemEntity getEntity(ItemEvent internal) {
        
        return internal.getEntity();
    }
    
}
