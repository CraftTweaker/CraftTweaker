package com.blamejared.crafttweaker.natives.event.level;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.neoforge.event.level.LevelEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("neoforge/api/event/level/LevelEvent")
@NativeTypeRegistration(value = LevelEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.level.LevelEvent")
public class ExpandLevelEvent {
    
    @ZenCodeType.Getter("level")
    public static LevelAccessor getLevel(LevelEvent internal) {
        
        return internal.getLevel();
    }
    
}
