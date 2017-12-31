package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerSleepInBedEvent")
@ZenRegister
public class PlayerSleepInBedEvent {

    private final IPlayer player;
    private final int x;
    private final int y;
    private final int z;

    public PlayerSleepInBedEvent(IPlayer player, int x, int y, int z) {
        this.player = player;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @ZenGetter("player")
    public IPlayer getPlayer() {
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
