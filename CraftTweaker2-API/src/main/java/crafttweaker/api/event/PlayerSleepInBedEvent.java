package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.player.IEntityPlayer;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerSleepInBedEvent")
@ZenRegister
public class PlayerSleepInBedEvent {
    
    private final IEntityPlayer player;
    private final int x;
    private final int y;
    private final int z;
    
    public PlayerSleepInBedEvent(IEntityPlayer player, int x, int y, int z) {
        this.player = player;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    @ZenGetter("player")
    public IEntityPlayer getPlayer() {
        return player;
    }
    
    @ZenGetter("x")
    public int getX() {
        return x;
    }
    
    @ZenGetter("y")
    public int getY() {
        return y;
    }
    
    @ZenGetter("z")
    public int getZ() {
        return z;
    }
}
