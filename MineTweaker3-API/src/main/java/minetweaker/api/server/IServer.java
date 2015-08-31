/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.server;

import minetweaker.api.event.IEventHandle;
import minetweaker.api.event.PlayerLoggedInEvent;
import minetweaker.api.event.PlayerLoggedOutEvent;
import minetweaker.api.player.IPlayer;
import minetweaker.util.IEventHandler;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author Stan
 */
@ZenClass("minetweaker.server.IServer")
public interface IServer {
	@ZenMethod
	public void addCommand(
			String name,
			String usage,
			String[] aliases,
			ICommandFunction function,
			@Optional ICommandValidator validator,
			@Optional ICommandTabCompletion completion);

	@ZenMethod
	public void removeCommand(String name);

	@ZenMethod
	public void addMineTweakerCommand(String name, String[] usage, ICommandFunction function);

	@ZenMethod
	public boolean isOp(IPlayer player);

	@ZenMethod
	public IEventHandle onPlayerLoggedIn(IEventHandler<PlayerLoggedInEvent> ev);

	@ZenMethod
	public IEventHandle onPlayerLoggedOut(IEventHandler<PlayerLoggedOutEvent> ev);
}
