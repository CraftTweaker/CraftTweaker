package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.damage.IDamageSource;
import crafttweaker.api.entity.IEntityItem;
import crafttweaker.api.player.IEntityPlayer;
import stanhebben.zenscript.annotations.*;

import java.util.List;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerDeathDropsEvent")
@ZenRegister
public class PlayerDeathDropsEvent {
    
    private final IEntityPlayer player;
    private final List<IEntityItem> items;
    private final IDamageSource damageSource;
    
    public PlayerDeathDropsEvent(IEntityPlayer player, List<IEntityItem> items, IDamageSource damageSource) {
        this.player = player;
        this.items = items;
        this.damageSource = damageSource;
    }
    
    @ZenGetter("player")
    public IEntityPlayer getPlayer() {
        return player;
    }
    
    @ZenGetter("items")
    public List<IEntityItem> getItems() {
        return items;
    }
    
    @ZenGetter("damageSource")
    public IDamageSource getDamageSource() {
        return damageSource;
    }
}
