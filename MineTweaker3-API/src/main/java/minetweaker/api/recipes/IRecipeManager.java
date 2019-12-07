package minetweaker.api.recipes;

import minetweaker.api.item.*;
import stanhebben.zenscript.annotations.*;

import java.util.List;

/**
 * The RecipeManager adds and removes crafting recipes. The IRecipeManager
 * instance is available through the recipes global variable.
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
    List<ICraftingRecipe> getAll();
    
    /**
     * Returns all crafting recipes resulting in the given ingredient.
     *
     * @param ingredient ingredient to find
     *
     * @return crafting recipes for the given item(s)
     */
    @ZenMethod
    List<ICraftingRecipe> getRecipesFor(IIngredient ingredient);
    
    /**
     * Removes all crafting recipes crafting the specified item.
     *
     * @param output recipe output pattern
     *
     * @return number of removed recipes
     */
    @ZenMethod
    int remove(IIngredient output, @Optional boolean nbtMatch);
    
    /**
     * Adds a shaped recipe.
     *
     * @param output      recipe output
     * @param ingredients recipe ingredients
     * @param function    recipe function (optional)
     */
    @ZenMethod
    void addShaped(IItemStack output, IIngredient[][] ingredients, @Optional IRecipeFunction function, @Optional IRecipeAction action);
    
    /**
     * Adds a mirrored shaped recipe.
     *
     * @param output      recipe output
     * @param ingredients recipe ingredients
     * @param function    recipe function (optional)
     */
    @ZenMethod
    void addShapedMirrored(IItemStack output, IIngredient[][] ingredients, @Optional IRecipeFunction function, @Optional IRecipeAction action);
    
    /**
     * Adds a shapeless recipe.
     *
     * @param output      recipe output
     * @param ingredients recipe ingredients
     * @param function    recipe function (optional)
     */
    @ZenMethod
    void addShapeless(IItemStack output, IIngredient[] ingredients, @Optional IRecipeFunction function, @Optional IRecipeAction action);
    
    /**
     * Removes shaped recipes.
     *
     * @param output      recipe output
     * @param ingredients recipe ingredients
     *
     * @return number of removed recipes
     */
    @ZenMethod
    int removeShaped(IIngredient output, @Optional IIngredient[][] ingredients);
    
    /**
     * Removes shapeless recipes.
     *
     * @param output      recipe output
     * @param ingredients recipe ingredients
     * @param wildcard    if the recipe may contain other ingredients too, besides
     *                    the ones specified
     *
     * @return number of removed recipes
     */
    @ZenMethod
    int removeShapeless(IIngredient output, @Optional IIngredient[] ingredients, @Optional boolean wildcard);
    
    /**
     * Performs a crafting with the specified ingredients. Returns null if no
     * crafting recipe exists.
     *
     * @param contents crafting inventory contents
     *
     * @return crafting result, or null
     */
    @ZenMethod
    IItemStack craft(IItemStack[][] contents);
    
}
