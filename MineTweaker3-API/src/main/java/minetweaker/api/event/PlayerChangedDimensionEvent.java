package minetweaker.api.event;

import minetweaker.api.player.IPlayer;
import minetweaker.api.world.IDimension;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("minetweaker.event.PlayerChangedDimensionEvent")
public class PlayerChangedDimensionEvent {

    private final IPlayer player;
    private final IDimension from;
    private final IDimension to;

    public PlayerChangedDimensionEvent(IPlayer player, IDimension from, IDimension to) {
        this.player = player;
        this.from = from;
        this.to = to;
    }

    @ZenGetter("player")
    public IPlayer getPlayer() {
        return player;
    }

    @ZenGetter("from")
    public IDimension getFrom() {
        return from;
    }

    @ZenGetter("to")
    public IDimension getTo() {
        return to;
    }
}
