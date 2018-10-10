package crafttweaker.api.server;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.command.ICommandManager;
import crafttweaker.api.command.ICommandSender;
import crafttweaker.api.event.*;
import crafttweaker.api.player.IPlayer;
import crafttweaker.util.IEventHandler;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.server.IServer")
@ZenRegister
public interface IServer extends ICommandSender {

    @ZenMethod
    boolean isOp(IPlayer player);
    
    @ZenMethod
    IEventHandle onPlayerLoggedIn(IEventHandler<PlayerLoggedInEvent> ev);
    
    @ZenMethod
    IEventHandle onPlayerLoggedOut(IEventHandler<PlayerLoggedOutEvent> ev);
    
    @ZenGetter("commandManager")
    ICommandManager getCommandManager();
    
    Object getInternal();
}
