/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.recipes;

import minetweaker.api.item.IItemStack;
import minetweaker.api.player.IPlayer;

/**
 *
 * @author Stan
 */
public class UnknownRecipe implements ICraftingRecipe {
	private final IItemStack output;

	public UnknownRecipe(IItemStack output) {
		this.output = output;
	}

	@Override
	public boolean matches(ICraftingInventory inventory) {
		return false;
	}

	@Override
	public IItemStack getCraftingResult(ICraftingInventory inventory) {
		return output;
	}

	@Override
	public boolean hasTransformers() {
		return false;
	}

	@Override
	public void applyTransformers(ICraftingInventory inventory, IPlayer byPlayer) {

	}

	@Override
	public String toCommandString() {
		return "// unknown recipe type for " + output;
	}
}
