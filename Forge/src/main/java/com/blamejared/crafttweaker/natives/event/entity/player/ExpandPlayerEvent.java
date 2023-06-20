package com.blamejared.crafttweaker.natives.event.entity.player;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("forge/api/event/entity/player/PlayerEvent")
@NativeTypeRegistration(value = PlayerEvent.class, zenCodeName = "crafttweaker.forge.api.event.entity.player.PlayerEvent")
public class ExpandPlayerEvent {
    
    @ZenCodeType.Getter("entity")
    public static Player getEntity(PlayerEvent internal) {
        
        return internal.getEntity();
    }
    
}
