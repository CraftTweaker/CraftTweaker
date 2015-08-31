/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.recipes;

import minetweaker.api.player.IPlayer;
import minetweaker.api.world.IDimension;

/**
 *
 * @author Stan
 */
public class CraftingInfo implements ICraftingInfo {
	private final ICraftingInventory inventory;
	private final IDimension dimension;

	public CraftingInfo(ICraftingInventory inventory, IDimension dimension) {
		this.inventory = inventory;
		this.dimension = dimension;
	}

	@Override
	public ICraftingInventory getInventory() {
		return inventory;
	}

	@Override
	public IPlayer getPlayer() {
		return inventory.getPlayer();
	}

	@Override
	public IDimension getDimension() {
		return dimension;
	}
}
