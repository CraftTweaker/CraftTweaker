package minetweaker;

import minetweaker.api.item.IIngredient;

/**
 * Used to register removable mod recipes for mod machines. Once registered, it
 * will be called when minetweaker.remove() is called.
 * 
 * @author Stan Hebben
 */
public interface IRecipeRemover {
	/**
	 * Removes all the items matching the given ingredient.
	 * 
	 * @param ingredient ingredient
	 */
	public void remove(IIngredient ingredient);
}
