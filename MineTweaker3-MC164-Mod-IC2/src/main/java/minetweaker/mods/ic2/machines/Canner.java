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
import minetweaker.api.liquid.ILiquidStack;
import static minetweaker.api.minecraft.MineTweakerMC.getItemStack;
import minetweaker.mods.ic2.IC2RecipeInput;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author Stan
 */
@ZenClass("mods.ic2.Canner")
@ModOnly("IC2")
public class Canner {
	@ZenMethod
	public static void addBottleRecipe(IIngredient container, IIngredient fill, IItemStack output) {
		MineTweakerAPI.apply(new AddBottleIngredientAction(container, fill, output));
	}
	
	@ZenMethod
	public static void addBottleRecipe(IIngredient container, ILiquidStack liquid, IItemStack output) {
		MineTweakerAPI.logError("Not supported in this mod support version");
	}
	
	@ZenMethod
	public static void addEnrichRecipe(ILiquidStack input, IIngredient additive, ILiquidStack output) {
		MineTweakerAPI.apply(new AddEnrichIngredientAction(input, additive, output));
	}
	
	@ZenMethod
	public static void addEnrichRecipe(ILiquidStack input, ILiquidStack additive, ILiquidStack output) {
		MineTweakerAPI.logError("Not supported in this mod support version");
	}
	
	private static class AddBottleIngredientAction extends OneWayAction {
		private final IIngredient container;
		private final IIngredient fill;
		private final IItemStack output;
		
		public AddBottleIngredientAction(IIngredient container, IIngredient fill, IItemStack output) {
			this.container = container;
			this.fill = fill;
			this.output = output;
		}
		
		@Override
		public void apply() {
			Recipes.cannerBottle.addRecipe(
					new IC2RecipeInput(container),
					new IC2RecipeInput(fill),
					getItemStack(output));
		}

		@Override
		public String describe() {
			return "Adding canner bottle recipe " + container + " + " + fill + " => " + output;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public int hashCode() {
			int hash = 7;
			hash = 67 * hash + (this.container != null ? this.container.hashCode() : 0);
			hash = 67 * hash + (this.fill != null ? this.fill.hashCode() : 0);
			hash = 67 * hash + (this.output != null ? this.output.hashCode() : 0);
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
			final AddBottleIngredientAction other = (AddBottleIngredientAction) obj;
			if (this.container != other.container && (this.container == null || !this.container.equals(other.container))) {
				return false;
			}
			if (this.fill != other.fill && (this.fill == null || !this.fill.equals(other.fill))) {
				return false;
			}
			if (this.output != other.output && (this.output == null || !this.output.equals(other.output))) {
				return false;
			}
			return true;
		}
	}
	
	private static class AddEnrichIngredientAction extends OneWayAction {
		private final ILiquidStack input;
		private final IIngredient additive;
		private final ILiquidStack output;
		
		public AddEnrichIngredientAction(ILiquidStack input, IIngredient additive, ILiquidStack output) {
			this.input = input;
			this.additive = additive;
			this.output = output;
		}

		@Override
		public void apply() {
			Recipes.cannerEnrich.addRecipe((FluidStack) input.getInternal(), new IC2RecipeInput(additive), (FluidStack) output.getInternal());
		}

		@Override
		public String describe() {
			return "Adding canner enrich recipe " + input + " + " + additive + " => " + output;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public int hashCode() {
			int hash = 7;
			hash = 47 * hash + (this.input != null ? this.input.hashCode() : 0);
			hash = 47 * hash + (this.additive != null ? this.additive.hashCode() : 0);
			hash = 47 * hash + (this.output != null ? this.output.hashCode() : 0);
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
			final AddEnrichIngredientAction other = (AddEnrichIngredientAction) obj;
			if (this.input != other.input && (this.input == null || !this.input.equals(other.input))) {
				return false;
			}
			if (this.additive != other.additive && (this.additive == null || !this.additive.equals(other.additive))) {
				return false;
			}
			if (this.output != other.output && (this.output == null || !this.output.equals(other.output))) {
				return false;
			}
			return true;
		}
	}
}
