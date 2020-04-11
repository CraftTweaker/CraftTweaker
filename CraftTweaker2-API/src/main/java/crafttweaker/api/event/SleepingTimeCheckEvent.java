package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("crafttweaker.event.SleepingTimeCheckEvent")
@ZenRegister
public interface SleepingTimeCheckEvent extends IPlayerEvent, IEventHasResult, IEventPositionable {
}
