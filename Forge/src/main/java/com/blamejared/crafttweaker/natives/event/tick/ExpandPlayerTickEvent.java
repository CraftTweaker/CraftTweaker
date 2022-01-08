package com.blamejared.crafttweaker.natives.event.tick;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/event/tick/PlayerTickEvent")
@NativeTypeRegistration(value = TickEvent.PlayerTickEvent.class, zenCodeName = "crafttweaker.api.event.tick.PlayerTickEvent")
public class ExpandPlayerTickEvent {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("player")
    public static Player getPlayer(TickEvent.PlayerTickEvent internal) {
        
        return internal.player;
    }
    
}
