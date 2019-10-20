package minetweaker.api.server;

import minetweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.ZenClass;

/**
 * @author Stan
 */
@ZenClass("minetweaker.server.ICommandTabCompletion")
public interface ICommandTabCompletion {

    String[] getTabCompletionOptions(String[] command, IPlayer player);
}
