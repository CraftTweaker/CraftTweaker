package com.blamejared.crafttweaker.natives.event.entity;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.EntityEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("forge/api/event/entity/EntityEvent")
@NativeTypeRegistration(value = EntityEvent.class, zenCodeName = "crafttweaker.api.event.entity.EntityEvent")
public class ExpandEntityEvent {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("entity")
    public static Entity getEntity(EntityEvent internal) {
        
        return internal.getEntity();
    }
    
}
