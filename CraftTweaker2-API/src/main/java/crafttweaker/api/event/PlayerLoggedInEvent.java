package crafttweaker.api.event;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.event.PlayerLoggedInEvent")
@ZenRegister
public class PlayerLoggedInEvent {

    private final IPlayer player;

    public PlayerLoggedInEvent(IPlayer player) {
        this.player = player;
    }

    @ZenGetter("player")
    public IPlayer getPlayer() {
        return player;
    }
}
