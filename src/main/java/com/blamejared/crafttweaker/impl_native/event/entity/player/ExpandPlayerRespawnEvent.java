package com.blamejared.crafttweaker.impl_native.event.entity.player;


import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.openzen.zencode.java.ZenCodeType;


/**
 * This Event is fired whenever a player respawns due to dying, or due to using the end portal.
 */
@ZenRegister
@Document("vanilla/api/event/entity/player/MCPlayerRespawnEvent")
@NativeTypeRegistration(value = PlayerEvent.PlayerRespawnEvent.class, zenCodeName = "crafttweaker.api.event.entity.player.MCPlayerRespawnEvent")
public class ExpandPlayerRespawnEvent {
    
    /**
     * Was this event caused by the player entering the portal in the end?
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("endConquered")
    public static boolean isEndConquered(PlayerEvent.PlayerRespawnEvent internal) {
        
        return internal.isEndConquered();
    }
    
}
