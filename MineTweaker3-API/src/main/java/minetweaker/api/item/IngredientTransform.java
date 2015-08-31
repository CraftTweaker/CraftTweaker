package minetweaker.api.item;

import minetweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * Transformations can be used to modify an ingredient after it is used in a
 * crafting recipe. It could reuse the item, damage it or consume multiple
 * items.
 * 
 * @author Stan Hebben
 */
@ZenExpansion("minetweaker.item.IIngredient")
public class IngredientTransform {
	/**
	 * Makes the item reusable. Prevents consumption of the item upon crafting.
	 * 
	 * @param ingredient target value
	 * @return reuse transformer
	 */
	@ZenMethod
	public static IIngredient reuse(IIngredient ingredient) {
		return ingredient.transform(new IItemTransformer() {
			@Override
			public IItemStack transform(IItemStack item, IPlayer byPlayer) {
				return item.withAmount(item.getAmount() + 1);
			}
		});
	}

	/**
	 * Damages the item. Also makes the item reusable. Will damage the item for
	 * 1 point upon crafting and consume it when broken.
	 * 
	 * @param ingredient target value
	 * @return damage transformer
	 */
	@ZenMethod
	public static IIngredient transformDamage(IIngredient ingredient) {
		return ingredient.transform(new IItemTransformer() {
			@Override
			public IItemStack transform(IItemStack item, IPlayer byPlayer) {
				int newDamage = item.getDamage() + 1;
				if (newDamage >= item.getMaxDamage()) {
					return item.withAmount(item.getAmount()).withDamage(0);
				} else {
					return item.withAmount(item.getAmount() + 1).withDamage(newDamage);
				}
			}
		});
	}

	/**
	 * Damages the item for a specific amount. Also makes the item reusable.
	 * Upon reaching maximum damage, the item will be consumed. Take care to set
	 * the proper condition such that an almost-broken item becomes invalid for
	 * crafting.
	 * 
	 * @param ingredient target value
	 * @param damage damage to be applied
	 * @return damage transformer
	 */
	@ZenMethod
	public static IIngredient transformDamage(IIngredient ingredient, final int damage) {
		return ingredient.transform(new IItemTransformer() {
			@Override
			public IItemStack transform(IItemStack item, IPlayer byPlayer) {
				System.out.println("Transform damage: " + item);
				int newDamage = item.getDamage() + damage;
				if (newDamage >= item.getMaxDamage()) {
					return item.withAmount(item.getAmount()).withDamage(0);
				} else {
					return item.withAmount(item.getAmount() + 1).withDamage(newDamage);
				}
			}
		});
	}

	/**
	 * Causes the item to be replaced upon crafting. Can be used, for instance,
	 * to return empty bottles or buckets.
	 * 
	 * @param ingredient target value
	 * @param withItem replacement item
	 * @return replacement transformer
	 */
	@ZenMethod
	public static IIngredient transformReplace(IIngredient ingredient, final IItemStack withItem) {
		return ingredient.transform(new IItemTransformer() {
			@Override
			public IItemStack transform(IItemStack item, IPlayer byPlayer) {
				if (item.getAmount() > 1) {
					byPlayer.give(withItem);
					return item;
				} else {
					return withItem.withAmount(withItem.getAmount() + 1);
				}
			}
		});
	}

	/**
	 * Causes multiple items to be consumed. Take care to set a condition for a
	 * minimum stack size too, as otherwise smaller stacks would still be
	 * accepted for input.
	 * 
	 * @param ingredient target value
	 * @param amount consumption amount
	 * @return consuming transformer
	 */
	@ZenMethod
	public static IIngredient transformConsume(IIngredient ingredient, final int amount) {
		return ingredient.transform(new IItemTransformer() {
			@Override
			public IItemStack transform(IItemStack item, IPlayer byPlayer) {
				return item.withAmount(Math.max(item.getAmount() - amount, 0) + 1);
			}
		});
	}

	/**
	 * Makes sure there is no return value.
	 * 
	 * @param ingredient
	 * @return
	 */
	@ZenMethod
	public static IIngredient noReturn(IIngredient ingredient) {
		return ingredient.transform(new IItemTransformer() {
			@Override
			public IItemStack transform(IItemStack item, IPlayer byPlayer) {
				return null;
			}
		});
	}

	/**
	 * Gives an item back to the player. Also clears the inventory slot at that
	 * position.
	 * 
	 * @param ingredient
	 * @param givenItem
	 * @return
	 */
	@ZenMethod
	public static IIngredient giveBack(IIngredient ingredient, @Optional final IItemStack givenItem) {
		return ingredient.transform(new IItemTransformer() {
			@Override
			public IItemStack transform(IItemStack item, IPlayer byPlayer) {
				if (givenItem == null) {
					byPlayer.give(item);
				} else {
					byPlayer.give(givenItem);
				}
				return null;
			}
		});
	}
}
