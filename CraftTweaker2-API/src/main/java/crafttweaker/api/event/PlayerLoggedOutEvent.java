package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerLoggedOutEvent")
@ZenRegister
public interface PlayerLoggedOutEvent extends IPlayerEvent {
}
