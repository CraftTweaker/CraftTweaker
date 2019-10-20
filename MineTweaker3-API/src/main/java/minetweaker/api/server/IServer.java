package minetweaker.api.server;

import minetweaker.api.event.*;
import minetweaker.api.player.IPlayer;
import minetweaker.util.IEventHandler;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("minetweaker.server.IServer")
public interface IServer {
    
    @ZenMethod
    void addCommand(String name, String usage, String[] aliases, ICommandFunction function, @Optional ICommandValidator validator, @Optional ICommandTabCompletion completion);
    
    @ZenMethod
    void addMineTweakerCommand(String name, String[] usage, ICommandFunction function);
    
    @ZenMethod
    boolean isOp(IPlayer player);
    
    @ZenMethod
    IEventHandle onPlayerLoggedIn(IEventHandler<PlayerLoggedInEvent> ev);
    
    @ZenMethod
    IEventHandle onPlayerLoggedOut(IEventHandler<PlayerLoggedOutEvent> ev);
    
    @ZenMethod
    boolean isCommandAdded(String name);
}
