package minetweaker.api.event;

import minetweaker.api.item.IItemStack;
import minetweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("minetweaker.event.PlayerPickupItemEvent")
public class PlayerPickupItemEvent {

    private final IPlayer player;
    private final IItemStack item;

    public PlayerPickupItemEvent(IPlayer player, IItemStack item) {
        this.player = player;
        this.item = item;
    }

    @ZenGetter("player")
    public IPlayer getPlayer() {
        return player;
    }

    @ZenGetter("item")
    public IItemStack getItem() {
        return item;
    }
}
