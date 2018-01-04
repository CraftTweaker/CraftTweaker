package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.player.IEntityPlayer;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerPickupItemEvent")
@ZenRegister
public class PlayerPickupItemEvent {
    
    private final IEntityPlayer player;
    private final IItemStack item;
    
    public PlayerPickupItemEvent(IEntityPlayer player, IItemStack item) {
        this.player = player;
        this.item = item;
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
