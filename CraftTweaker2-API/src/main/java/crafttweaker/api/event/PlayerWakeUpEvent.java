package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenSetter;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerWakeUpEvent")
@ZenRegister
public interface PlayerWakeUpEvent extends IPlayerEvent {

    @ZenGetter("shouldSetSpawn")
    public boolean shouldSetSpawn();

    @ZenGetter("shouldUpdateWorld")
    public boolean shouldUpdateWorld();

    @ZenGetter("shouldWakeImmediately")
    public boolean shouldWakeImmediately();
}
