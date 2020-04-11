package crafttweaker.mc1120.events.handling;

import crafttweaker.api.event.PlayerLoggedInEvent;
import crafttweaker.api.player.IPlayer;

/**
 * @author Stan
 */
public class MCPlayerLoggedInEvent implements PlayerLoggedInEvent {

    private final IPlayer player;

    public MCPlayerLoggedInEvent(IPlayer player) {
        this.player = player;
    }

    @Override
    public IPlayer getPlayer() {
        return player;
    }
}
