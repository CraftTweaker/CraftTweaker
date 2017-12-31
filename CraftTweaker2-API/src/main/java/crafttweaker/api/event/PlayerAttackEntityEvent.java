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
@ZenClass("crafttweaker.event.PlayerAttackEntityEvent")
@ZenRegister
public class PlayerAttackEntityEvent {

    private final IPlayer player;
    private final IEntity entity;
    private boolean canceled;

    public PlayerAttackEntityEvent(IPlayer player, IEntity entity) {
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
