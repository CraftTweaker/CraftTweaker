package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerRespawnEvent")
@ZenRegister
public interface PlayerRespawnEvent extends IPlayerEvent {

    @ZenGetter("endConquered")
    boolean isEndConquered();
}
