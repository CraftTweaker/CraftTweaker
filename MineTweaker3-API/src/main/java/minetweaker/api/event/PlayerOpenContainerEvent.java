/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.event;

import minetweaker.api.container.IContainer;
import minetweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author Stan
 */
@ZenClass("minetweaker.event.PlayerOpenContainerEvent")
public class PlayerOpenContainerEvent {
	private final IPlayer player;
	private final IContainer container;
	private boolean canceled;

	public PlayerOpenContainerEvent(IPlayer player, IContainer container) {
		this.player = player;
		this.container = container;

		canceled = false;
	}

	@ZenMethod
	public void cancel() {
		canceled = true;
	}

	@ZenGetter("canceled")
	public boolean isCanceled() {
		return canceled;
	}

	@ZenGetter("player")
	public IPlayer getPlayer() {
		return player;
	}

	@ZenGetter("container")
	public IContainer getContainer() {
		return container;
	}
}
