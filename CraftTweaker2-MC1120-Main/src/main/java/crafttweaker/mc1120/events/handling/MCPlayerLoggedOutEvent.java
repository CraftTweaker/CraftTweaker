package crafttweaker.mc1120.events.handling;

import crafttweaker.api.event.PlayerLoggedOutEvent;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.ZenGetter;

/**
 * @author Stan
 */
public class MCPlayerLoggedOutEvent implements PlayerLoggedOutEvent {
    
    private final IPlayer player;
    
    public MCPlayerLoggedOutEvent(IPlayer player) {
        this.player = player;
    }
    
    @Override
    public IPlayer getPlayer() {
        return player;
    }
}
