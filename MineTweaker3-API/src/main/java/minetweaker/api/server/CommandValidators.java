package minetweaker.api.server;

import minetweaker.MineTweakerAPI;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("minetweaker.server.CommandValidators")
public class CommandValidators {

    public static final ICommandValidator ISOP = player -> MineTweakerAPI.server.isOp(player);

    @ZenGetter
    public static ICommandValidator isOp() {
        return ISOP;
    }
}
