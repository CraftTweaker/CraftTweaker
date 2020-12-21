package com.blamejared.crafttweaker.impl_native.event.entity;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.EntityEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/event/MCEntityEvent")
@NativeTypeRegistration(value = EntityEvent.class, zenCodeName = "crafttweaker.api.event.entity.MCEntityEvent")
public class ExpandEntityEvent {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("entity")
    public static Entity getEntity(EntityEvent internal) {
        return internal.getEntity();
    }
}
