package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.*;

@ZenClass("crafttweaker.event.PlayerTickEvent")
@ZenRegister
public interface PlayerTickEvent extends IPlayerEvent {
    
    @ZenGetter("phase")
    String getPhase();
}
