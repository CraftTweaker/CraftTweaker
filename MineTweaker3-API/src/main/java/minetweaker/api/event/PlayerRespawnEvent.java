package minetweaker.api.event;

import minetweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("minetweaker.event.PlayerRespawnEvent")
public class PlayerRespawnEvent {

    private final IPlayer player;

    public PlayerRespawnEvent(IPlayer player) {
        this.player = player;
    }

    @ZenGetter("player")
    public IPlayer getPlayer() {
        return player;
    }
}
