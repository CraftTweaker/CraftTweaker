package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.player.IEntityPlayer;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerUseItemStartEvent")
@ZenRegister
public class PlayerUseItemStartEvent implements IEventCancelable {
    
    private final IEntityPlayer player;
    private final IItemStack item;
    private boolean canceled;
    
    public PlayerUseItemStartEvent(IEntityPlayer player, IItemStack item) {
        this.player = player;
        this.item = item;
        
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
    
    @ZenGetter("item")
    public IItemStack getItem() {
        return item;
    }
}
