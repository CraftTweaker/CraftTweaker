package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerPickupItemEvent")
@ZenRegister
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
