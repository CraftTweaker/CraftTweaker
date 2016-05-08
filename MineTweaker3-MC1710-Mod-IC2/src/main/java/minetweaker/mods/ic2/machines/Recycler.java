/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mods.ic2.machines;

import ic2.api.recipe.Recipes;
import minetweaker.MineTweakerAPI;
import minetweaker.OneWayAction;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import static minetweaker.api.minecraft.MineTweakerMC.getItemStack;
import minetweaker.mods.ic2.IC2RecipeInput;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author Stan
 */
@ZenClass("mods.ic2.Recycler")
@ModOnly("IC2")
public class Recycler {
	@ZenMethod
	public static void addBlacklist(IIngredient ingredient) {
		MineTweakerAPI.apply(new AddBlacklistAction(ingredient));
	}
	
	@ZenMethod
	public static boolean isBlacklisted(IItemStack item) {
		return Recipes.recyclerBlacklist.contains(getItemStack(item));
	}
	
	private static class AddBlacklistAction extends OneWayAction {
		private IIngredient ingredient;
		
		public AddBlacklistAction(IIngredient ingredient) {
			this.ingredient = ingredient;
		}

		@Override
		public void apply() {
			Recipes.recyclerBlacklist.add(new IC2RecipeInput(ingredient));
		}

		@Override
		public String describe() {
			return "Adding " + ingredient + " to the recycler blacklist";
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public int hashCode() {
			int hash = 3;
			hash = 29 * hash + (this.ingredient != null ? this.ingredient.hashCode() : 0);
			return hash;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final AddBlacklistAction other = (AddBlacklistAction) obj;
			if (this.ingredient != other.ingredient && (this.ingredient == null || !this.ingredient.equals(other.ingredient))) {
				return false;
			}
			return true;
		}
	}
}
