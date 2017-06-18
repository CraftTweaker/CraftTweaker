package crafttweaker.api.server;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.server.CommandValidators")
@ZenRegister
public class CommandValidators {
    
    public static final ICommandValidator ISOP = player -> CraftTweakerAPI.server.isOp(player);
    
    
    @ZenGetter
    public static ICommandValidator getISOP() {
        return ISOP;
    }
}
