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
import minetweaker.api.item.WeightedItemStack;
import static minetweaker.api.minecraft.MineTweakerMC.getItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author Stan
 */
@ZenClass("mods.ic2.ScrapBox")
@ModOnly("IC2")
public class ScrapBox {
	/**
	 * Adds an item drop.
	 * 
	 * Reference scrap box chance values:
	 *
	 * 0.1: Diamond
	 * 0.5: Cake, Gold Helmet, Iron Ore, Gold Ore
	 * 1.0: Wooden tools, Soul Sand, Sign, Leather, Feather, Bone
	 * 1.5: Apple, Bread
	 * 2.0: Netherrack, Rotten Flesh
	 * 3.0: Grass, Gravel
	 * 4.0: Stick
	 * 5.0: Dirt, Wooden Hoe
	 * 
	 * @param stack weighted item stack to add
	 */
	@ZenMethod
	public static void addDrop(WeightedItemStack stack) {
		MineTweakerAPI.apply(new AddDropAction(stack));
	}
	
	private static class AddDropAction extends OneWayAction {
		private final WeightedItemStack stack;
		
		public AddDropAction(WeightedItemStack stack) {
			this.stack = stack;
		}

		@Override
		public void apply() {
			Recipes.scrapboxDrops.addDrop(getItemStack(stack.getStack()), stack.getChance());
		}

		@Override
		public String describe() {
			return "Adding scrapbox drop " + stack.getStack();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public int hashCode() {
			int hash = 5;
			hash = 71 * hash + (this.stack != null ? this.stack.hashCode() : 0);
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
			final AddDropAction other = (AddDropAction) obj;
			if (this.stack != other.stack && (this.stack == null || !this.stack.equals(other.stack))) {
				return false;
			}
			return true;
		}
	}
}
