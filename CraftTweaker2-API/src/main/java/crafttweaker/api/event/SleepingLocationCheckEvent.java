package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("crafttweaker.event.SleepingLocationCheckEvent")
@ZenRegister
public interface SleepingLocationCheckEvent extends IPlayerEvent, IEventHasResult, IEventPositionable {
}
