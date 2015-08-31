package minetweaker.api.client;

import minetweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

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
	public IPlayer getPlayer();

	@ZenGetter("language")
	public String getLanguage();
}
