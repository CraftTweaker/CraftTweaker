package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.player.IEntityPlayer;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerAttackEntityEvent")
@ZenRegister
public class PlayerAttackEntityEvent implements IEventCancelable {
    
    private final IEntityPlayer player;
    private final IEntity entity;
    private boolean canceled;
    
    public PlayerAttackEntityEvent(IEntityPlayer player, IEntity entity) {
        this.player = player;
        this.entity = entity;
        canceled = false;
    }
    
    @Override
    public void cancel() {
        canceled = true;
    }
    
    @Override
    public boolean isCanceled() {
        return canceled;
    }
    
    @ZenGetter("player")
    public IEntityPlayer getPlayer() {
        return player;
    }
    
    @ZenGetter("entity")
    public IEntity getEntity() {
        return entity;
    }
}
