/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.event;

import minetweaker.api.item.IItemStack;
import minetweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.ZenGetter;

/**
 *
 * @author Stan
 */
public class PlayerSmeltedEvent {
	private final IPlayer player;
	private final IItemStack output;

	public PlayerSmeltedEvent(IPlayer player, IItemStack output) {
		this.player = player;
		this.output = output;
	}

	@ZenGetter("player")
	public IPlayer getPlayer() {
		return player;
	}

	@ZenGetter("output")
	public IItemStack getOutput() {
		return output;
	}
}
