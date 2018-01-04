package crafttweaker.api.client;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.player.IEntityPlayer;
import stanhebben.zenscript.annotations.*;

/**
 * Interface for client interaction. Only available on clients.
 *
 * @author Stan Hebben
 */
@ZenClass("crafttweaker.api.IClient")
@ZenRegister
public interface IClient {
    
    /**
     * Gets the current player.
     *
     * @return current player
     */
    @ZenGetter("player")
    IEntityPlayer getPlayer();
    
    @ZenGetter("language")
    String getLanguage();
}
