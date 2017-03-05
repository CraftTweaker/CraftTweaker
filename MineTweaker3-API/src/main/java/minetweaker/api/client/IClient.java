package minetweaker.api.client;

import minetweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.*;

/**
 * Interface for client interaction. Only available on clients.
 *
 * @author Stan Hebben
 */
@ZenClass("minetweaker.api.IClient")
public interface IClient {
    
    /**
     * Gets the current player.
     *
     * @return current player
     */
    @ZenGetter("player")
    IPlayer getPlayer();
    
    @ZenGetter("language")
    String getLanguage();
}
