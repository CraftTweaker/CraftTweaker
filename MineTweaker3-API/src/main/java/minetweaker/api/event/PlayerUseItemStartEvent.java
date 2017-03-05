package minetweaker.api.event;

import minetweaker.api.item.IItemStack;
import minetweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("minetweaker.event.PlayerUseItemStartEvent")
public class PlayerUseItemStartEvent {

    private final IPlayer player;
    private final IItemStack item;
    private boolean canceled;

    public PlayerUseItemStartEvent(IPlayer player, IItemStack item) {
        this.player = player;
        this.item = item;

        canceled = false;
    }

    @ZenMethod
    public void cancel() {
        canceled = true;
    }

    @ZenGetter("canceled")
    public boolean isCanceled() {
        return canceled;
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
