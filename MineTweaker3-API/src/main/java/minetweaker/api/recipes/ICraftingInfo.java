/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.recipes;

import minetweaker.api.player.IPlayer;
import minetweaker.api.world.IDimension;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;

/**
 * 
 * @author Stan Hebben
 */
@ZenClass("minetweaker.recipes.ICraftingInfo")
public interface ICraftingInfo {
	@ZenGetter("inventory")
	public ICraftingInventory getInventory();

	@ZenGetter("player")
	public IPlayer getPlayer();

	@ZenGetter("dimension")
	public IDimension getDimension();
}
