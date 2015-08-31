package minetweaker.api.server;

import minetweaker.MineTweakerImplementationAPI;
import minetweaker.api.event.IEventHandle;
import minetweaker.api.event.PlayerLoggedInEvent;
import minetweaker.api.event.PlayerLoggedOutEvent;
import minetweaker.util.IEventHandler;

/**
 * Abstract server implementation. Implements call of which the implementation
 * is already fixed by the API.
 * 
 * @author Stan Hebben
 */
public abstract class AbstractServer implements IServer {
	@Override
	public final void addMineTweakerCommand(String name, String[] usage, ICommandFunction function) {
		MineTweakerImplementationAPI.addMineTweakerCommand(name, usage, function);
	}

	@Override
	public final IEventHandle onPlayerLoggedIn(IEventHandler<PlayerLoggedInEvent> ev) {
		return MineTweakerImplementationAPI.events.onPlayerLoggedIn(ev);
	}

	@Override
	public final IEventHandle onPlayerLoggedOut(IEventHandler<PlayerLoggedOutEvent> ev) {
		return MineTweakerImplementationAPI.events.onPlayerLoggedOut(ev);
	}
}
