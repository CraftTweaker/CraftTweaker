package minetweaker.api.item;

import minetweaker.api.data.IData;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * Conditions can be used to set requirements for matching items. They can be
 * used to require specific NBT tags or damage values, requiring changed items
 * or as complement to damage-consuming or stack-consuming recipes.
 * 
 * @author Stan Hebben
 */
@ZenExpansion("minetweaker.item.IIngredient")
public class IngredientCondition {
	/**
	 * Requires the item to be damaged. A damage value of 1 is enough.
	 * 
	 * @param ingredient target value
	 * @return damaged condition
	 */
	@ZenMethod
	public static IIngredient onlyDamaged(IIngredient ingredient) {
		return ingredient.only(new IItemCondition() {
			@Override
			public boolean matches(IItemStack stack) {
				return stack.getDamage() > 0;
			}
		});
	}

	/**
	 * Requires the item to be damaged with at least a specified amount.
	 * 
	 * @param ingredient target value
	 * @param minDamage minimum damage
	 * @return damage condition
	 */
	@ZenMethod
	public static IIngredient onlyDamageAtLeast(IIngredient ingredient, final int minDamage) {
		return ingredient.only(new IItemCondition() {
			@Override
			public boolean matches(IItemStack stack) {
				return stack.getDamage() >= minDamage;
			}
		});
	}

	/**
	 * Requires the item to be damaged with at most a specified amount. An
	 * undamaged item is accepted.
	 * 
	 * @param ingredient target value
	 * @param maxDamage maximum damage
	 * @return damage condition
	 */
	@ZenMethod
	public static IIngredient onlyDamageAtMost(IIngredient ingredient, final int maxDamage) {
		return ingredient.only(new IItemCondition() {
			@Override
			public boolean matches(IItemStack stack) {
				return stack.getDamage() <= maxDamage;
			}
		});
	}

	/**
	 * Requires the item damage value to be between two values.
	 * 
	 * @param ingredient target value
	 * @param minDamage minimum damage
	 * @param maxDamage maximum damage
	 * @return damage condition
	 */
	@ZenMethod
	public static IIngredient onlyDamageBetween(IIngredient ingredient, final int minDamage, final int maxDamage) {
		return ingredient.only(new IItemCondition() {
			@Override
			public boolean matches(IItemStack stack) {
				return stack.getDamage() >= minDamage && stack.getDamage() <= maxDamage;
			}
		});
	}

	/**
	 * Requires the item to contain at least the given tags. It is OK for the
	 * item to contain more data, as remaining data is ignored.
	 * 
	 * @param ingredient target value
	 * @param data required data
	 * @return data condition
	 */
	@ZenMethod
	public static IIngredient onlyWithTag(IIngredient ingredient, final IData data) {
		return ingredient.only(new IItemCondition() {
			@Override
			public boolean matches(IItemStack stack) {
				return stack.getTag() != null && stack.getTag().contains(data);
			}
		});
	}

	/**
	 * Requires the input stack to contain at least the given number of items.
	 * Used in combination with stack consuming recipes.
	 * 
	 * @param ingredient target value
	 * @param amount required stack size
	 * @return stack size condition
	 */
	@ZenMethod
	public static IIngredient onlyStack(IIngredient ingredient, final int amount) {
		return ingredient.only(new IItemCondition() {
			@Override
			public boolean matches(IItemStack stack) {
				return stack.getAmount() >= amount;
			}
		});
	}
}
