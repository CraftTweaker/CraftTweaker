/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.event;

import minetweaker.api.entity.IEntityXp;
import minetweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author Stan
 */
@ZenClass("minetweaker.event.PlayerPickupXpEvent")
public class PlayerPickupXpEvent {
	private final IPlayer player;
	private final IEntityXp xp;
	private boolean canceled;

	public PlayerPickupXpEvent(IPlayer player, IEntityXp xp) {
		this.player = player;
		this.xp = xp;

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

	@ZenGetter("entity")
	public IEntityXp getEntity() {
		return xp;
	}

	@ZenGetter("xp")
	public float getXp() {
		return xp.getXp();
	}
}
