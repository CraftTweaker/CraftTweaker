package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.player.IEntityPlayer;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerRespawnEvent")
@ZenRegister
public class PlayerRespawnEvent {
    
    private final IEntityPlayer player;
    
    public PlayerRespawnEvent(IEntityPlayer player) {
        this.player = player;
    }
    
    @ZenGetter("player")
    public IEntityPlayer getPlayer() {
        return player;
    }
}
