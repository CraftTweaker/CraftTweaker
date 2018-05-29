package crafttweaker.api.item;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.*;

/**
 * Transformations can be used to modify an ingredient after it is used in a
 * crafting recipe. It could reuse the item, damage it or consume multiple
 * items.
 *
 * @author Stan Hebben
 */
@ZenExpansion("crafttweaker.item.IIngredient")
@ZenRegister
public class IngredientTransform {
    
    /**
     * Damages the item. Also makes the item reusable. Will damage the item for
     * 1 point upon crafting and consume it when broken.
     *
     * @param ingredient target value
     *
     * @return damage transformer
     */
    @ZenMethod
    public static IIngredient transformDamage(IIngredient ingredient) {
        return transformDamage(ingredient, 1);
    }
    
    /**
     * Damages the item for a specific amount. Also makes the item reusable.
     * Upon reaching maximum damage, the item will be consumed. Take care to set
     * the proper condition such that an almost-broken item becomes invalid for
     * crafting.
     *
     * @param ingredient target value
     * @param damage     damage to be applied
     *
     * @return damage transformer
     */
    @ZenMethod
    public static IIngredient transformDamage(IIngredient ingredient, final int damage) {
        return ingredient.transformNew(item -> (item.getDamage() + damage > item.getMaxDamage()) ? null : item.withDamage(item.getDamage() + damage));
    }
    
    /**
     * Causes the item to be replaced upon crafting. Can be used, for instance,
     * to return empty bottles or buckets.
     *
     * @param ingredient target value
     * @param withItem   replacement item
     *
     * @return replacement transformer
     */
    @ZenMethod
    public static IIngredient transformReplace(IIngredient ingredient, final IItemStack withItem) {
        return ingredient.transformNew(item -> withItem);
    }
    
    /**
     * Causes multiple items to be consumed. Take care to set a condition for a
     * minimum stack size too, as otherwise smaller stacks would still be
     * accepted for input.
     *
     * @param ingredient target value
     * @param amount     consumption amount
     *
     * @return consuming transformer
     */
    @ZenMethod
    public static IIngredient transformConsume(IIngredient ingredient, final int amount) {
        return ingredient.transform((item, player) -> {
            int newAmount = Math.max(item.getAmount() - amount, 0);
            return newAmount == 0 ? null : item.withAmount(newAmount);
        });
    }
    
    /**
     * Makes sure there is no return value.
     *
     * @param ingredient
     *
     * @return
     */
    @ZenMethod
    public static IIngredient noReturn(IIngredient ingredient) {
        return ingredient.transformNew(item -> null);
    }
    
    /**
     * Gives an item back to the player. Also clears the inventory slot at that
     * position.
     *
     * @param ingredient
     * @param givenItem
     *
     * @return
     */
    @ZenMethod
    public static IIngredient giveBack(IIngredient ingredient, @Optional final IItemStack givenItem) {
        return ingredient.transform((item, byPlayer) -> {
            byPlayer.give(givenItem == null ? item : givenItem);
        return null;});
    }
    
    /**
     * Leaves the item in the crafting grid.
     * @param ingredient
     * @return
     */
    
    @ZenMethod
    public static IIngredient reuse(IIngredient ingredient) {
        return ingredient.transformNew(item -> item.withAmount(1));
    }
}
