package minetweaker.minecraft.recipes;

import minetweaker.minecraft.item.IIngredient;
import minetweaker.minecraft.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * The RecipeManager adds and removes crafting recipes. The IRecipeManager
 * instance is available throug the recipes global variable.
 * 
 * @author Stan Hebben
 */
@ZenClass("minecraft.recipes.IRecipeManager")
public interface IRecipeManager {
	/**
	 * Removes all crafting recipes crafting the specified item.
	 * 
	 * @param output recipe output pattern
	 * @return number of removed recipes
	 */
	@ZenMethod
	public int remove(IIngredient output);
	
	/**
	 * Adds a shaped recipe.
	 * 
	 * @param output recipe output
	 * @param ingredients recipe ingredients
	 * @param function recipe function (optional)
	 * @param mirrored indicates if the mirrored recipe should be valid too (optional)
	 */
	@ZenMethod
	public void addShaped(
			IItemStack output,
			IIngredient[][] ingredients,
			@Optional IRecipeFunction function,
			@Optional boolean mirrored);
	
	/**
	 * Adds a shapeless recipe.
	 * 
	 * @param output recipe output
	 * @param ingredients recipe ingredients
	 * @param function recipe function (optional)
	 */
	@ZenMethod
	public void addShapeless(
			IItemStack output,
			IIngredient[] ingredients,
			@Optional IRecipeFunction function);
	
	/**
	 * Removes shaped recipes.
	 * 
	 * @param output recipe output
	 * @param ingredients recipe ingredients
	 * @return number of removed recipes
	 */
	@ZenMethod
	public int removeShaped(
			IIngredient output,
			@Optional IIngredient[][] ingredients);
	
	/**
	 * Removes shapeless recipes.
	 * 
	 * @param output recipe output
	 * @param ingredients recipe ingredients
	 * @param wildcard if the recipe may contain other ingredients too, besides the ones specified
	 * @return number of removed recipes
	 */
	@ZenMethod
	public int removeShapeless(
			IIngredient output,
			@Optional IIngredient[] ingredients,
			@Optional boolean wildcard);
	
	/**
	 * Performs a crafting with the specified ingredients. Returns null if no
	 * crafting recipe exists.
	 * 
	 * @param contents crafting inventory contents
	 * @return crafting result, or null
	 */
	@ZenMethod
	public IItemStack craft(IItemStack[][] contents);
}
