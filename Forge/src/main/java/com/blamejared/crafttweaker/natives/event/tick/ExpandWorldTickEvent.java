package com.blamejared.crafttweaker.natives.event.tick;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("forge/api/event/tick/LevelTickEvent")
@NativeTypeRegistration(value = TickEvent.LevelTickEvent.class, zenCodeName = "crafttweaker.api.event.tick.LevelTickEvent")
public class ExpandWorldTickEvent {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("level")
    public static Level getLevel(TickEvent.LevelTickEvent internal) {
        
        return internal.level;
    }
    
}
