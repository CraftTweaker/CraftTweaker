/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.event;

import minetweaker.api.item.IItemStack;
import minetweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author Stan
 */
@ZenClass("minetweaker.event.PlayerUseItemTickEvent")
public class PlayerUseItemTickEvent {
	private final IPlayer player;
	private final IItemStack item;
	private final int duration;
	private boolean canceled;

	public PlayerUseItemTickEvent(IPlayer player, IItemStack item, int duration) {
		this.player = player;
		this.item = item;
		this.duration = duration;

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

	@ZenGetter("item")
	public IItemStack getItem() {
		return item;
	}

	@ZenGetter("duration")
	public int getDuration() {
		return duration;
	}
}
