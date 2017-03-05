package minetweaker.api.recipes;

import minetweaker.api.item.IItemStack;
import stanhebben.zenscript.annotations.*;

import java.util.List;

/**
 * The BrewingManager adds and removes brewing recipes. The IBrewingManager
 * instance is available through the recipes global variable.
 *
 * @author Jared
 */
@ZenClass("minetweaker.recipes.IBrewingManager")
public interface IBrewingManager {
    
    /**
     * Returns all crafting recipes.
     *
     * @return all crafting recipes
     */
    @ZenGetter("all")
    List<IBrewingRecipe> getAll();
    
    /**
     * Removes a brewing recipe from the specified item.
     *
     * @param output recipe output pattern
     */
    @ZenMethod
    void remove(IItemStack output);
    
    /**
     * Adds a recipe.
     *
     * @param output     recipe output
     * @param ingredient recipe ingredient
     * @param input      recipe input
     */
    @ZenMethod
    void add(IItemStack output, IItemStack ingredient, IItemStack input);
    
    /**
     * Removes recipes.
     *
     * @param output      recipe output
     * @param ingredients recipe ingredient
     *
     * @return number of removed recipes
     */
    @ZenMethod
    int remove(IItemStack output, IItemStack ingredients);
    
}
