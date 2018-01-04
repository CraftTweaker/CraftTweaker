package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.player.IEntityPlayer;
import crafttweaker.api.world.IWorld;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerChangedDimensionEvent")
@ZenRegister
public class PlayerChangedDimensionEvent {
    
    private final IEntityPlayer player;
    private final IWorld from;
    private final IWorld to;
    
    public PlayerChangedDimensionEvent(IEntityPlayer player, IWorld from, IWorld to) {
        this.player = player;
        this.from = from;
        this.to = to;
    }
    
    @ZenGetter("player")
    public IEntityPlayer getPlayer() {
        return player;
    }
    
    @ZenGetter("from")
    public int getFrom() {
        return from.getDimension();
    }
    
    @ZenGetter("to")
    public int getTo() {
        return to.getDimension();
    }
    
    @ZenGetter("fromWorld")
    public IWorld getFromWorld() {
        return from;
    }
    
    @ZenGetter("toWorld")
    public IWorld getToWorld() {
        return to;
    }
}
