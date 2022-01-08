package com.blamejared.crafttweaker.natives.event.entity.player;


import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.openzen.zencode.java.ZenCodeType;


/**
 * This Event is fired whenever a player respawns due to dying, or due to using the end portal.
 */
@ZenRegister
@Document("vanilla/api/event/entity/player/PlayerRespawnEvent")
@NativeTypeRegistration(value = PlayerEvent.PlayerRespawnEvent.class, zenCodeName = "crafttweaker.api.event.entity.player.PlayerRespawnEvent")
public class ExpandPlayerRespawnEvent {
    
    /**
     * Was this event caused by the player entering the portal in the end?
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("isEndConquered")
    public static boolean isEndConquered(PlayerEvent.PlayerRespawnEvent internal) {
        
        return internal.isEndConquered();
    }
    
}
