/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.recipes;

import minetweaker.api.item.IItemStack;
import minetweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.ZenClass;

/**
 *
 * @author Stan
 */
@ZenClass("minetweaker.recipes.ICraftingRecipe")
public interface ICraftingRecipe {
	public boolean matches(ICraftingInventory inventory);

	public IItemStack getCraftingResult(ICraftingInventory inventory);

	public boolean hasTransformers();

	public void applyTransformers(ICraftingInventory inventory, IPlayer byPlayer);

	public String toCommandString();
}
