package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerRespawnEvent")
@ZenRegister
public interface PlayerRespawnEvent extends IPlayerEvent {
    
    @ZenGetter("endConquered")
    boolean isEndConquered();
}
