/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.minecraft.recipes;

import minetweaker.minecraft.item.IIngredient;
import minetweaker.minecraft.item.IItemStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author Stan
 */
public interface IFurnaceManager {
	@ZenMethod
	public void remove(IIngredient ingredient);
	
	@ZenMethod
	public void addRecipe(IIngredient output, IIngredient input, @Optional double xp);
	
	@ZenMethod
	public void setFuel(IIngredient item, int fuel);
	
	@ZenMethod
	public int getFuel(IItemStack item);
}
