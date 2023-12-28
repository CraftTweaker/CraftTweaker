package com.blamejared.crafttweaker.natives.event.level;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.ForgeEventBusWire;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.level.LevelEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("forge/api/event/level/LevelEvent")
@NativeTypeRegistration(value = LevelEvent.class, zenCodeName = "crafttweaker.forge.api.event.level.LevelEvent")
public class ExpandLevelEvent {
    
    @ZenCodeType.Getter("level")
    public static LevelAccessor getLevel(LevelEvent internal) {
        
        return internal.getLevel();
    }
    
}
