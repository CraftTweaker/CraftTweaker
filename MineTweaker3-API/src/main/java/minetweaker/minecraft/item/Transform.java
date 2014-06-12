/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.minecraft.item;

import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author Stan
 */
@ZenClass("minecraft.item.Transform")
public class Transform {
	/**
	 * Makes the item reusable. Prevents consumption of the item upon crafting.
	 * 
	 * @return reuse transformer
	 */
	@ZenMethod
	public static IItemTransformer reuse() {
		return new IItemTransformer() {
			@Override
			public IItemStack transform(IItemStack item) {
				return item.withAmount(item.getAmount() + 1);
			}
		};
	}
	
	/**
	 * Damages the item. Also makes the item reusable. Will damage the item
	 * for 1 point upon crafting and consume it when broken.
	 * 
	 * @return damage transformer
	 */
	@ZenMethod
	public static IItemTransformer damage() {
		return new IItemTransformer() {
			@Override
			public IItemStack transform(IItemStack item) {
				int newDamage = item.getDamage() + 1;
				if (newDamage >= item.getMaxDamage()) {
					return item.withAmount(item.getAmount() - 1).withDamage(0);
				} else {
					return item.withDamage(newDamage);
				}
			}
		};
	}
	
	/**
	 * Damages the item for a specific amount. Also makes the item reusable.
	 * Upon reaching maximum damage, the item will be consumed. Take care to
	 * set the proper condition such that an almost-broken item becomes
	 * invalid for crafting.
	 * 
	 * @param damage damage to be applied
	 * @return damage transformer
	 */
	@ZenMethod
	public static IItemTransformer damage(final int damage) {
		return new IItemTransformer() {
			@Override
			public IItemStack transform(IItemStack item) {
				int newDamage = item.getDamage() + damage;
				if (newDamage >= item.getMaxDamage()) {
					return item.withAmount(item.getAmount() - 1).withDamage(newDamage - item.getMaxDamage());
				} else {
					return item.withDamage(newDamage);
				}
			}
		};
	}
	
	/**
	 * Causes the item to be replaced upon crafting. Can be used, for instance,
	 * to return empty bottles or buckets.
	 * 
	 * @param withItem replacement item
	 * @return replacement transformer
	 */
	@ZenMethod
	public static IItemTransformer replaceWith(IItemStack withItem) {
		final IItemStack result = withItem.withAmount(withItem.getAmount() + 1);
		
		return new IItemTransformer() {
			@Override
			public IItemStack transform(IItemStack item) {
				return result;
			}
		};
	}
	
	/**
	 * Causes multiple items to be consumed. Take care to set a condition for
	 * a minimum stack size too, as otherwise smaller stacks would still be
	 * accepted for input.
	 * 
	 * @param amount consumption amount
	 * @return consuming transformer
	 */
	@ZenMethod
	public static IItemTransformer consume(final int amount) {
		return new IItemTransformer() {
			@Override
			public IItemStack transform(IItemStack item) {
				return item.withAmount(Math.max(item.getAmount() - amount, 0) + 1);
			}
		};
	}
}
