package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.*;

@ZenRegister
@ZenClass("crafttweaker.event.PlayerEvent")
public interface PlayerEvent {
    
    @ZenGetter("player")
    IPlayer getPlayer();
    
}
