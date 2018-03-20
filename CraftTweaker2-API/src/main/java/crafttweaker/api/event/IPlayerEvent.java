package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.*;

@ZenRegister
@ZenClass("crafttweaker.event.IPlayerEvent")
public interface IPlayerEvent {
    
    @ZenGetter("player")
    IPlayer getPlayer();
    
}
