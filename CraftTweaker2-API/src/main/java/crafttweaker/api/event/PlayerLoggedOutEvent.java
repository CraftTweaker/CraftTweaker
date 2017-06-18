package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerLoggedOutEvent")
@ZenRegister
public class PlayerLoggedOutEvent {
    
    private final IPlayer player;
    
    public PlayerLoggedOutEvent(IPlayer player) {
        this.player = player;
    }
    
    @ZenGetter("player")
    public IPlayer getPlayer() {
        return player;
    }
}
