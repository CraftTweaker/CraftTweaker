package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerRespawnEvent")
@ZenRegister
public interface PlayerRespawnEvent extends PlayerEvent {
    
    @ZenGetter("endConquered")
    boolean isEndConquered();
}
