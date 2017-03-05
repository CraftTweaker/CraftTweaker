package minetweaker.api.server;

import minetweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.ZenClass;

/**
 * @author Stan
 */
@ZenClass("minetweaker.server.ICommandFunction")
public interface ICommandFunction {

    void execute(String[] arguments, IPlayer player);
}
