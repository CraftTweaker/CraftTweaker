package crafttweaker.api.server;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.event.*;
import crafttweaker.api.player.IEntityPlayer;
import crafttweaker.util.IEventHandler;
import stanhebben.zenscript.annotations.*;

/**
 * @author Stan
 */
@ZenClass("crafttweaker.server.IServer")
@ZenRegister
public interface IServer {

    @ZenMethod
    boolean isOp(IEntityPlayer player);
    
    @ZenMethod
    IEventHandle onPlayerLoggedIn(IEventHandler<PlayerLoggedInEvent> ev);
    
    @ZenMethod
    IEventHandle onPlayerLoggedOut(IEventHandler<PlayerLoggedOutEvent> ev);
}
