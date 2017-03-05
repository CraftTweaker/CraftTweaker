package minetweaker.api.item;

import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.*;

import java.util.List;

/**
 * Represents a recipe ingredient. An ingredient can be an item, an ore
 * dictionary entry, an item stack, an ore dictionary entry multiplied with an
 * amount, or other kinds of ingredients as offered by the API.
 * <p>
 * There is no guarantee that every mod will respect the conditions or
 * transformations, or that every mod will support every kind of ingredient.
 *
 * @author Stan Hebben
 */
@ZenClass("minetweaker.item.IIngredient")
public interface IIngredient {
    
    /**
     * Gets the mark of the ingredient. Ingredients can be marked so they can be
     * used in crafting functions.
     *
     * @return ingredient mark
     */
    @ZenGetter("mark")
    String getMark();
    
    /**
     * Gets the amount.
     * <p>
     * Should return -1 if no amount is available and 1 for a single item.
     * Stacks return the stack size.
     *
     * @return stack size
     */
    @ZenGetter("amount")
    int getAmount();
    
    /**
     * Gets all possible items for this ingredient.
     * <p>
     * If there is no item list (for example, it is the &lt;*&gt; wildcard item)
     * null should be returned.
     *
     * @return the items for this ingredient, or null
     */
    @ZenGetter("items")
    List<IItemStack> getItems();
    
    /**
     * Gets all possible liquids for this ingredient.
     * <p>
     * If there is no liquid list (for example, it is the &lt;*&ft; wildcard
     * item) null should be returned.
     *
     * @return the liquids for this ingredient, or null
     */
    @ZenGetter("liquids")
    List<ILiquidStack> getLiquids();
    
    /**
     * Returns a new ingredient with the given stack size.
     *
     * @param amount new stack size
     *
     * @return modified ingredient
     */
    @ZenOperator(OperatorType.MUL)
    @ZenMethod
    IIngredient amount(int amount);
    
    /**
     * Combines multiple ingredients into a single one. Note that ore dictionary
     * entries are preferred to or statements.
     *
     * @param ingredient ingredient to combine with
     *
     * @return or-ed ingredient
     */
    @ZenOperator(OperatorType.OR)
    @ZenMethod
    IIngredient or(IIngredient ingredient);
    
    /**
     * Returns a new ingredient with the given transform added to it.
     *
     * @param transformer transformer to add
     *
     * @return modified ingredient
     */
    @ZenMethod
    IIngredient transform(IItemTransformer transformer);
    
    /**
     * Returns a new ingredient with the given condition added to it.
     *
     * @param condition condition to add
     *
     * @return modified ingredient
     */
    @ZenMethod
    IIngredient only(IItemCondition condition);
    
    /**
     * Returns a new ingredient marked with the given name.
     *
     * @param mark mark to apply
     *
     * @return modified ingredient
     */
    @ZenMethod
    IIngredient marked(String mark);
    
    /**
     * Checks if this ingredient matches the given item. For liquids, will match
     * the item if it is a valid container.
     *
     * @param item item to check
     *
     * @return true if the item matches
     */
    @ZenMethod
    boolean matches(IItemStack item);
    
    /**
     * Checks if this ingredient matches the given item. For liquids, will match
     * the item if it is a valid container.
     *
     * @param item item to check
     *
     * @return true if the item matches
     */
    @ZenMethod
    boolean matchesExact(IItemStack item);
    
    
    /**
     * Checks if this ingredient matches the given liquid.
     *
     * @param liquid liquid to check
     *
     * @return true if the liquid matches
     */
    @ZenMethod
    boolean matches(ILiquidStack liquid);
    
    /**
     * Check if this ingredient contains all possible values for the given
     * ingredient.
     *
     * @param ingredient ingredient to check
     *
     * @return true if the ingredient contains the given one
     */
    @ZenOperator(OperatorType.CONTAINS)
    boolean contains(IIngredient ingredient);
    
    /**
     * Applies transformations after crafting, if any, to the given item.
     *
     * @param item     item to transform
     * @param byPlayer player performing the crafting operation
     *
     * @return transformed item
     */
    @ZenMethod
    IItemStack applyTransform(IItemStack item, IPlayer byPlayer);
    
    /**
     * Checks if this ingredient has (or could have) any transformatiosns.
     *
     * @return true if there are (or could be) any transformations false
     * otherwise.
     */
    @ZenGetter("hasTransformations")
    boolean hasTransformers();
    
    /**
     * Gets the internal item backing this ingredient.
     * <p>
     * The value is implementation-dependent and should only be handled by the
     * internal code. Don't use this value - instead, use the version-specific
     * helper methods.
     *
     * @return internal item
     */
    Object getInternal();
    
}
