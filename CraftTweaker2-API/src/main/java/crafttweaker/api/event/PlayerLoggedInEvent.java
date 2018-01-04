package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.player.IEntityPlayer;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerLoggedInEvent")
@ZenRegister
public class PlayerLoggedInEvent {
    
    private final IEntityPlayer player;
    
    public PlayerLoggedInEvent(IEntityPlayer player) {
        this.player = player;
    }
    
    @ZenGetter("player")
    public IEntityPlayer getPlayer() {
        return player;
    }
}
