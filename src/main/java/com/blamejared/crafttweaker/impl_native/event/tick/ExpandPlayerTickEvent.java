package com.blamejared.crafttweaker.impl_native.event.tick;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.TickEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/event/tick/MCPlayerTickEvent")
@NativeTypeRegistration(value = TickEvent.PlayerTickEvent.class, zenCodeName = "crafttweaker.api.event.tick.MCPlayerTickEvent")
public class ExpandPlayerTickEvent {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("player")
    public static PlayerEntity getPlayer(TickEvent.PlayerTickEvent internal) {
        
        return internal.player;
    }
    
}
