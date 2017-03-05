package minetweaker.api.server;

import minetweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.ZenClass;

/**
 * @author Stan
 */
@ZenClass("minetweaker.server.ICommandValidator")
public interface ICommandValidator {

    boolean canExecute(IPlayer player);
}
