package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

@ZenClass("crafttweaker.event.PlayerCloneEvent")
@ZenRegister
public interface PlayerCloneEvent extends IPlayerEvent {

    @ZenGetter("wasDeath")
    boolean wasDeath();

    @ZenGetter("originalPlayer")
    IPlayer getOriginal();
}