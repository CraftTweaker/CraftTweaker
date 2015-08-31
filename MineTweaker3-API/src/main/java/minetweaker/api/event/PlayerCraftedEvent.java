/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.event;

import minetweaker.api.item.IItemStack;
import minetweaker.api.player.IPlayer;
import minetweaker.api.recipes.ICraftingInventory;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

/**
 *
 * @author Stan Hebben
 */
@ZenClass("minetweaker.event.PlayerCraftingEvent")
public class PlayerCraftedEvent {
	private final IPlayer player;
	private final IItemStack output;
	private final ICraftingInventory inventory;

	public PlayerCraftedEvent(IPlayer player, IItemStack output, ICraftingInventory inventory) {
		this.player = player;
		this.output = output;
		this.inventory = inventory;
	}

	@ZenGetter("player")
	public IPlayer getPlayer() {
		return player;
	}

	@ZenGetter("output")
	public IItemStack getOutput() {
		return output;
	}

	@ZenGetter("inventory")
	public ICraftingInventory getInventory() {
		return inventory;
	}
}
