package minetweaker.mods.gregtech.machines;

import static gregtech.api.enums.GT_Values.RA;

import minetweaker.MineTweakerAPI;
import minetweaker.OneWayAction;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.gregtech.BrewingMachine")
public class BrewingMachine {
	private static final class AddBrewingRecipeAction extends OneWayAction {
		private boolean hidden;
		private IItemStack catalyst;
		private ILiquidStack input;
		private ILiquidStack output;

		public AddBrewingRecipeAction(ILiquidStack output, ILiquidStack input, IItemStack catalyst, boolean hidden) {
			this.output = output;
			this.input = input;
			this.catalyst = catalyst;
			this.hidden = hidden;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public String describe() {
			return "Adding recipe for brewing " + output.getDisplayName();
		}

		@Override
		public void apply() {
			RA.addBrewingRecipe(MineTweakerMC.getItemStack(catalyst)
					, MineTweakerMC.getLiquidStack(input).getFluid()
					, MineTweakerMC.getLiquidStack(output).getFluid()
					, hidden);
		}
	}

	@ZenMethod
	public static void addBrewingRecipe(ILiquidStack output, ILiquidStack input, IItemStack catalyst, boolean hidden) {
		MineTweakerAPI.apply(new AddBrewingRecipeAction(output, input, catalyst, hidden));
	}
}
