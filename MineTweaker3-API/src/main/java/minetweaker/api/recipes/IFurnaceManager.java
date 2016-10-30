/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.recipes;

import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.List;

/**
 *
 * @author Stan
 */
@ZenClass("minetweaker.recipes.IFurnaceManager")
public interface IFurnaceManager {
	@ZenMethod
    void remove(IIngredient output, @Optional IIngredient input);

	@ZenMethod
    void addRecipe(IItemStack output, IIngredient input, @Optional double xp);

	@ZenMethod
    void setFuel(IIngredient item, int fuel);

	@ZenMethod
    int getFuel(IItemStack item);

	/**
	 * Returns all crafting recipes.
	 *
	 * @return all crafting recipes
	 */
	@ZenGetter("all")
    List<IFurnaceRecipe> getAll();
}
