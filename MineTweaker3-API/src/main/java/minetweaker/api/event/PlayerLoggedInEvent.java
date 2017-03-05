package minetweaker.api.event;

import minetweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("minetweaker.event.PlayerLoggedInEvent")
public class PlayerLoggedInEvent {
    
    private final IPlayer player;
    
    public PlayerLoggedInEvent(IPlayer player) {
        this.player = player;
    }
    
    @ZenGetter("player")
    public IPlayer getPlayer() {
        return player;
    }
}
