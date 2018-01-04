package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntityXp;
import crafttweaker.api.player.IEntityPlayer;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerPickupXpEvent")
@ZenRegister
public class PlayerPickupXpEvent implements IEventCancelable {
    
    private final IEntityPlayer player;
    private final IEntityXp xp;
    private boolean canceled;
    
    public PlayerPickupXpEvent(IEntityPlayer player, IEntityXp xp) {
        this.player = player;
        this.xp = xp;
        
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
    public IEntityXp getEntity() {
        return xp;
    }
    
    @ZenGetter("xp")
    public float getXp() {
        return xp.getXp();
    }
}
