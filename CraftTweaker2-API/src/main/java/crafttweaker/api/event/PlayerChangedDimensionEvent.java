package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.player.IPlayer;
import crafttweaker.api.world.IDimension;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerChangedDimensionEvent")
@ZenRegister
public class PlayerChangedDimensionEvent {
    
    private final IPlayer player;
    private final IDimension from;
    private final IDimension to;
    
    public PlayerChangedDimensionEvent(IPlayer player, IDimension from, IDimension to) {
        this.player = player;
        this.from = from;
        this.to = to;
    }
    
    @ZenGetter("player")
    public IPlayer getPlayer() {
        return player;
    }
    
    @ZenGetter("from")
    public IDimension getFrom() {
        return from;
    }
    
    @ZenGetter("to")
    public IDimension getTo() {
        return to;
    }
}
