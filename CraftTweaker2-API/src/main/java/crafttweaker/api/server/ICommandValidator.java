package crafttweaker.api.server;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.ZenClass;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.server.ICommandValidator")
@ZenRegister
public interface ICommandValidator {
    
    boolean canExecute(IPlayer player);
}
