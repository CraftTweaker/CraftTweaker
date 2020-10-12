package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenClass("crafttweaker.event.PlayerTickEvent")
@ZenRegister
public interface PlayerTickEvent extends IPlayerEvent, ITickEvent {

    @ZenGetter("phase")
    String getPhase();
}
