package com.blamejared.crafttweaker.natives.event.entity;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import org.openzen.zencode.java.ZenCodeType;

/**
 * @docEvent canceled it will deny the entity to join the level
 */
@ZenRegister
@Document("forge/api/event/entity/EntityJoinLevelEvent")
@NativeTypeRegistration(value = EntityJoinLevelEvent.class, zenCodeName = "crafttweaker.api.event.entity.EntityJoinLevelEvent")
public class ExpandEntityJoinWorldEvent {
    
    @ZenCodeType.Getter("level")
    public static Level getLevel(EntityJoinLevelEvent internal) {
        
        return internal.getLevel();
    }
    
}
