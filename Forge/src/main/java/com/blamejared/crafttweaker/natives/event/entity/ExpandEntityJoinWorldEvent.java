package com.blamejared.crafttweaker.natives.event.entity;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docEvent canceled it will deny the entity to join the world
 */
@ZenRegister
@Document("vanilla/api/event/entity/EntityJoinWorldEvent")
@NativeTypeRegistration(value = EntityJoinWorldEvent.class, zenCodeName = "crafttweaker.api.event.entity.EntityJoinWorldEvent")
public class ExpandEntityJoinWorldEvent {
    
    @ZenCodeType.Getter("world")
    public static Level getWorld(EntityJoinWorldEvent internal) {
        
        return internal.getWorld();
    }
    
}
