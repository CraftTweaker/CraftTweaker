package minetweaker.api.event;

import minetweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("minetweaker.event.PlayerLoggedOutEvent")
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
