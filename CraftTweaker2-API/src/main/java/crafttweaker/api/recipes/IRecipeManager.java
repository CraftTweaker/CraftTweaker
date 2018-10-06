package crafttweaker.api.recipes;

import crafttweaker.annotations.*;
import crafttweaker.api.item.*;
import stanhebben.zenscript.annotations.*;

import java.util.List;

/**
 * The RecipeManager adds and removes crafting recipes. The IRecipeManager
 * instance is available through the recipes global variable.
 *
 * @author Stan Hebben
 */
@ZenClass("crafttweaker.recipes.IRecipeManager")
@ZenRegister
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
     * Adds a shaped recipe.
     *
     * @param output      recipe output
     * @param ingredients recipe ingredients
     * @param function    recipe function (optional)
     */
    @ZenMethod
    void addShaped(IItemStack output, IIngredient[][] ingredients, @Optional IRecipeFunction function, @Optional IRecipeAction action);
    
    /**
     * Adds a shaped recipe.
     *
     * @param output      recipe output
     * @param ingredients recipe ingredients
     * @param function    recipe function (optional)
     */
    @ZenDoc("Adds a shaped recipe.")
    @ZenMethod
    void addShaped(String name, IItemStack output, IIngredient[][] ingredients, @Optional IRecipeFunction function, @Optional IRecipeAction action);
    
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
     * Adds a mirrored shaped recipe.
     *
     * @param output      recipe output
     * @param ingredients recipe ingredients
     * @param function    recipe function (optional)
     */
    @ZenMethod
    void addShapedMirrored(String name, IItemStack output, IIngredient[][] ingredients, @Optional IRecipeFunction function, @Optional IRecipeAction action);
    
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
     * Adds a shapeless recipe.
     *
     * @param output      recipe output
     * @param ingredients recipe ingredients
     * @param function    recipe function (optional)
     */
    @ZenMethod
    void addShapeless(String name, IItemStack output, IIngredient[] ingredients, @Optional IRecipeFunction function, @Optional IRecipeAction action);
    
    /**
     * @param name        recipe name
     * @param output      recipe output
     * @param ingredients recipe ingredients
     * @param function    recipe function (optional)
     * @param action      recipe action (optional)
     */
    @ZenMethod
    void addHiddenShapeless(String name, IItemStack output, IIngredient[] ingredients, @Optional IRecipeFunction function, @Optional IRecipeAction action);
    
    /**
     * @param name        recipe name
     * @param output      recipe output
     * @param ingredients recipe ingredients
     * @param function    recipe function (optional)
     * @param action      recipe action (optional)
     * @param mirrored    is mirrored (optional)
     */
    @ZenMethod
    void addHiddenShaped(String name, IItemStack output, IIngredient[][] ingredients, @Optional IRecipeFunction function, @Optional IRecipeAction action, @Optional boolean mirrored);
    /**
     * Removes all crafting recipes in the game
     *
     * @return amount of recipes removed.
     */
    @ZenMethod
    void removeAll();
    
    /**
     * Removes all crafting recipes crafting the specified item.
     *
     * @param output recipe output pattern
     *
     */
    @ZenMethod
    void remove(IIngredient output, @Optional boolean nbtMatch);
    
    /**
     * Removes the recipe with the given registry name
     *
     * @param recipeName: RegistryName of the recipe
     */
    @ZenMethod
    void removeByRecipeName(String recipeName, @Optional IItemStack outputFilter);
    
    /**
     * Removes all recipe which match the given regex String
     *
     * @param regexString: Regex String to match to
     */
    @ZenMethod
    void removeByRegex(String regexString, @Optional IItemStack outputFilter);
    
    /**
     * Removes all recipe which match the given modid
     *
     * @param modid: modid of recipes to remove
     */
    @ZenMethod
    void removeByMod(String modid);
    
    /**
     * Removes shaped recipes.
     *
     * @param output      recipe output
     * @param ingredients recipe ingredients
     *
     */
    @ZenMethod
    void removeShaped(IIngredient output, @Optional IIngredient[][] ingredients);
    
    /**
     * Removes shapeless recipes.
     *
     * @param output      recipe output
     * @param ingredients recipe ingredients
     * @param wildcard    if the recipe may contain other ingredients too, besides
     *                    the ones specified
     *
     */
    @ZenMethod
    void removeShapeless(IIngredient output, @Optional IIngredient[] ingredients, @Optional boolean wildcard);
    
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


    @ZenMethod
    void replaceAllOccurences(IIngredient toReplace, IIngredient replaceWith, @Optional IIngredient forOutput);
}
