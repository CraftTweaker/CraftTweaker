package minetweaker.api.recipes;

import java.util.List;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * The RecipeManager adds and removes crafting recipes. The IRecipeManager
 * instance is available throug the recipes global variable.
 * 
 * @author Stan Hebben
 */
@ZenClass("minetweaker.recipes.IRecipeManager")
public interface IRecipeManager {
	/**
	 * Returns all crafting recipes.
	 * 
	 * @return all crafting recipes
	 */
	@ZenGetter("all")
	public List<ICraftingRecipe> getAll();

	/**
	 * Returns all crafting recipes resulting in the given ingredient.
	 * 
	 * @param ingredient ingredient to find
	 * @return crafting recipes for the given item(s)
	 */
	@ZenMethod
	public List<ICraftingRecipe> getRecipesFor(IIngredient ingredient);

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
	 */
	@ZenMethod
	public void addShaped(
			IItemStack output,
			IIngredient[][] ingredients,
			@Optional IRecipeFunction function);

	/**
	 * Adds a mirrored shaped recipe.
	 * 
	 * @param output recipe output
	 * @param ingredients recipe ingredients
	 * @param function recipe function (optional)
	 */
	@ZenMethod
	public void addShapedMirrored(
			IItemStack output,
			IIngredient[][] ingredients,
			@Optional IRecipeFunction function);

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
	 * @param wildcard if the recipe may contain other ingredients too, besides
	 *        the ones specified
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
