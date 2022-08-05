package com.blamejared.crafttweaker.natives.event.entity;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraftforge.event.entity.item.ItemEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("forge/api/event/entity/ItemEvent")
@NativeTypeRegistration(value = ItemEvent.class, zenCodeName = "crafttweaker.api.event.entity.ItemEvent")
public class ExpandItemEvent {
    
    @ZenCodeType.Getter("entityItem")
    public static ItemEntity getEntityItem(ItemEvent internal) {
        
        return internal.getEntity();
    }
    
}
