package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerUseItemTickEvent")
@ZenRegister
public class PlayerUseItemTickEvent implements IEventCancelable {
    
    private final IPlayer player;
    private final IItemStack item;
    private final int duration;
    private boolean canceled;
    
    public PlayerUseItemTickEvent(IPlayer player, IItemStack item, int duration) {
        this.player = player;
        this.item = item;
        this.duration = duration;
        
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
    public IPlayer getPlayer() {
        return player;
    }
    
    @ZenGetter("item")
    public IItemStack getItem() {
        return item;
    }
    
    @ZenGetter("duration")
    public int getDuration() {
        return duration;
    }
}
