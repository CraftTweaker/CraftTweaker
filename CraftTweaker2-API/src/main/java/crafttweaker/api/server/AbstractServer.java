package crafttweaker.api.server;

import crafttweaker.CrafttweakerImplementationAPI;
import crafttweaker.api.event.*;
import crafttweaker.util.IEventHandler;

/**
 * Abstract server implementation. Implements call of which the implementation
 * is already fixed by the API.
 *
 * @author Stan Hebben
 */
public abstract class AbstractServer implements IServer {
    
    @Override
    public final IEventHandle onPlayerLoggedIn(IEventHandler<PlayerLoggedInEvent> ev) {
        return CrafttweakerImplementationAPI.events.onPlayerLoggedIn(ev);
    }
    
    @Override
    public final IEventHandle onPlayerLoggedOut(IEventHandler<PlayerLoggedOutEvent> ev) {
        return CrafttweakerImplementationAPI.events.onPlayerLoggedOut(ev);
    }
}
