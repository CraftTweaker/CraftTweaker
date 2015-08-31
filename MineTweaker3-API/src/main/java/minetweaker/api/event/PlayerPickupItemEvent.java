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

/**
 *
 * @author Stan
 */
@ZenClass("minetweaker.event.PlayerPickupItemEvent")
public class PlayerPickupItemEvent {
	private final IPlayer player;
	private final IItemStack item;

	public PlayerPickupItemEvent(IPlayer player, IItemStack item) {
		this.player = player;
		this.item = item;
	}

	@ZenGetter("player")
	public IPlayer getPlayer() {
		return player;
	}

	@ZenGetter("item")
	public IItemStack getItem() {
		return item;
	}
}
