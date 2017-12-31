package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerPickupEvent")
@ZenRegister
public class PlayerPickupEvent {

    private final IPlayer player;
    private final IEntity entity;
    private boolean canceled;
    private boolean processed;

    public PlayerPickupEvent(IPlayer player, IEntity entity) {
        this.player = player;
        this.entity = entity;

        canceled = false;
        processed = false;
    }

    @ZenMethod
    public void cancel() {
        canceled = true;
    }

    /**
     * Processes the event (picks up the entity).
     */
    @ZenMethod
    public void process() {
        processed = true;
    }

    @ZenGetter("canceled")
    public boolean isCanceled() {
        return canceled;
    }

    @ZenGetter("processed")
    public boolean isProcessed() {
        return processed;
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
