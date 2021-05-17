package com.blamejared.crafttweaker.impl_native.event.entity;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.entity.item.ItemEntity;
import net.minecraftforge.event.entity.item.ItemEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/event/entity/MCItemEvent")
@NativeTypeRegistration(value = ItemEvent.class, zenCodeName = "crafttweaker.api.event.entity.MCItemEvent")
public class ExpandItemEvent {
    
    @ZenCodeType.Getter("entityItem")
    public static ItemEntity getEntityItem(ItemEvent internal) {
        
        return internal.getEntityItem();
    }
    
}
