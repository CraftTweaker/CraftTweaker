/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.item;

import minetweaker.api.data.IData;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * Conditions can be used to set requirements for matching items. They can be
 * used to require specific NBT tags or damage values, requiring changed items
 * or as complement to damage-consuming or stack-consuming recipes.
 * 
 * @author Stan Hebben
 */
@ZenClass("minetweaker.item.Condition")
public class Condition {
	/**
	 * Requires the item to be damaged. A damage value of 1 is enough.
	 * 
	 * @return damaged condition
	 */
	@ZenGetter
	public static IItemCondition damaged() {
		return new IItemCondition() {
			@Override
			public boolean matches(IItemStack stack) {
				return stack.getDamage() > 0;
			}
		};
	}
	
	/**
	 * Requires the item to be damaged with at least a specified amount.
	 * 
	 * @param minDamage minimum damage
	 * @return damage condition
	 */
	@ZenMethod
	public static IItemCondition damageAtLeast(final int minDamage) {
		return new IItemCondition() {
			@Override
			public boolean matches(IItemStack stack) {
				return stack.getDamage() >= minDamage;
			}
		};
	}
	
	/**
	 * Requires the item to be damaged with at most a specified amount. An
	 * undamaged item is accepted.
	 * 
	 * @param maxDamage maximum damage
	 * @return damage condition
	 */
	@ZenMethod
	public static IItemCondition damageAtMost(final int maxDamage) {
		return new IItemCondition() {
			@Override
			public boolean matches(IItemStack stack) {
				return stack.getDamage() <= maxDamage;
			}
		};
	}
	
	/**
	 * Requires the item damage value to be between two values.
	 * 
	 * @param minDamage minimum damage
	 * @param maxDamage maximum damage
	 * @return damage condition
	 */
	@ZenMethod
	public static IItemCondition damageBetween(final int minDamage, final int maxDamage) {
		return new IItemCondition() {
			@Override
			public boolean matches(IItemStack stack) {
				return stack.getDamage() >= minDamage && stack.getDamage() <= maxDamage;
			}
		};
	}
	
	/**
	 * Requires the item to contain at least the given tags. It is OK for the
	 * item to contain more data, as remaining data is ignored.
	 * 
	 * @param data required data
	 * @return data condition
	 */
	@ZenMethod
	public static IItemCondition data(final IData data) {
		return new IItemCondition() {
			@Override
			public boolean matches(IItemStack stack) {
				return stack.getTag() != null && stack.getTag().contains(data);
			}
		};
	}
	
	/**
	 * Requires the input stack to contain at least the given number of items.
	 * Used in combination with stack consuming recipes.
	 * 
	 * @param amount required stack size
	 * @return stack size condition
	 */
	@ZenMethod
	public static IItemCondition stack(final int amount) {
		return new IItemCondition() {
			@Override
			public boolean matches(IItemStack stack) {
				return stack.getAmount() >= amount;
			}
		};
	}
}
