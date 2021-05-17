package com.blamejared.crafttweaker.impl_native.event.tick;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/event/tick/MCWorldTickEvent")
@NativeTypeRegistration(value = TickEvent.WorldTickEvent.class, zenCodeName = "crafttweaker.api.event.tick.MCWorldTickEvent")
public class ExpandWorldTickEvent {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("world")
    public static World getWorld(TickEvent.WorldTickEvent internal) {
        
        return internal.world;
    }
    
}
