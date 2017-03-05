package minetweaker.api.event;

import minetweaker.api.entity.IEntity;
import minetweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("minetweaker.event.PlayerInteractEntityEvent")
public class PlayerInteractEntityEvent {

    private final IPlayer player;
    private final IEntity entity;
    private boolean canceled;

    public PlayerInteractEntityEvent(IPlayer player, IEntity entity) {
        this.player = player;
        this.entity = entity;
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

    @ZenGetter("entity")
    public IEntity getEntity() {
        return entity;
    }
}
