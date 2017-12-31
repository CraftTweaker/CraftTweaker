package crafttweaker.api.client;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

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
    IPlayer getPlayer();

    @ZenGetter("language")
    String getLanguage();
}
