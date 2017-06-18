package crafttweaker.api.server;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.ZenClass;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.server.ICommandTabCompletion")
@ZenRegister
public interface ICommandTabCompletion {
    
    String[] getTabCompletionOptions(String[] command, IPlayer player);
}
