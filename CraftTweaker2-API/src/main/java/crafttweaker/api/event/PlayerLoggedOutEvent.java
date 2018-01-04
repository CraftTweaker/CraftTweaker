package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.player.IEntityPlayer;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerLoggedOutEvent")
@ZenRegister
public class PlayerLoggedOutEvent {
    
    private final IEntityPlayer player;
    
    public PlayerLoggedOutEvent(IEntityPlayer player) {
        this.player = player;
    }
    
    @ZenGetter("player")
    public IEntityPlayer getPlayer() {
        return player;
    }
}
