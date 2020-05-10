package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerSleepInBedEvent")
@ZenRegister
public interface PlayerSleepInBedEvent extends IPlayerEvent, IEventPositionable {
}
