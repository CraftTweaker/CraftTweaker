package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("crafttweaker.event.PlayerTickEvent")
@ZenRegister
public interface PlayerTickEvent extends IPlayerEvent, ITickEvent {

}
