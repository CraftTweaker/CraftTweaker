package crafttweaker.api.server;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.ZenClass;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.server.ICommandFunction")
@ZenRegister
public interface ICommandFunction {
    
    void execute(String[] arguments, IPlayer player);
}
