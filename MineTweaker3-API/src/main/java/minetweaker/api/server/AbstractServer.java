package minetweaker.api.server;

import minetweaker.MineTweakerImplementationAPI;
import minetweaker.api.event.IEventHandle;
import minetweaker.api.event.IPlayerLoggedInEventHandler;
import minetweaker.api.event.IPlayerLoggedOutEventHandler;

/**
 * Abstract server implementation. Implements call of which the implementation is
 * already fixed by the API.
 * 
 * @author Stan Hebben
 */
public abstract class AbstractServer implements IServer {
	@Override
	public final void addMineTweakerCommand(String name, String[] usage, ICommandFunction function) {
		MineTweakerImplementationAPI.addMineTweakerCommand(name, usage, function);
	}

	@Override
	public final IEventHandle onPlayerLoggedIn(IPlayerLoggedInEventHandler ev) {
		return MineTweakerImplementationAPI.events.onPlayerLoggedIn(ev);
	}

	@Override
	public final IEventHandle onPlayerLoggedOut(IPlayerLoggedOutEventHandler ev) {
		return MineTweakerImplementationAPI.events.onPlayerLoggedOut(ev);
	}
}
