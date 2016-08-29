/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mods.ic2.expand;

import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemCondition;
import minetweaker.api.item.IItemStack;
import minetweaker.api.item.IItemTransformer;
import minetweaker.api.player.IPlayer;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author Stan
 */
@ZenExpansion("minetweaker.item.IIngredient")
@ModOnly("IC2")
public class IngredientExpansion {
	@ZenMethod
	public static IIngredient onlyIC2ChargeAtLeast(IIngredient ingredient, final int charge) {
		return ingredient.only(new IItemCondition() {
			@Override
			public boolean matches(IItemStack stack) {
				return ItemExpansion.getIC2Charge(stack) >= charge;
			}
		});
	}
	
	@ZenMethod
	public static IIngredient onlyIC2ChargeAtMost(IIngredient ingredient, final int charge) {
		return ingredient.only(new IItemCondition() {
			@Override
			public boolean matches(IItemStack stack) {
				return ItemExpansion.getIC2Charge(stack) <= charge;
			}
		});
	}
	
	@ZenMethod
	public static IIngredient transformIC2Discharge(IIngredient ingredient, final int amount) {
		return ingredient.transform(new IItemTransformer() {
			@Override
			public IItemStack transform(IItemStack item, IPlayer byPlayer) {
				return ItemExpansion.ic2Discharge(item, amount, 0).withAmount(item.getAmount() + 1);
			}
		});
	}
}
