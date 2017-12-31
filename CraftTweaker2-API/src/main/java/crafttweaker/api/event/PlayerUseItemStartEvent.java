package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerUseItemStartEvent")
@ZenRegister
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
