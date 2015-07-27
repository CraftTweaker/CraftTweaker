package minetweaker.mods.gregtech.machines;

import static gregtech.api.enums.GT_Values.RA;

import minetweaker.MineTweakerAPI;
import minetweaker.OneWayAction;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.gregtech.FluidCanner")
public class FluidCanner {
	private static final class AddFluidCanningAction extends OneWayAction {
		private IItemStack output;
		private ILiquidStack lOutput;
		private ILiquidStack lInput;
		private IItemStack input;

		public AddFluidCanningAction(IItemStack output, ILiquidStack lOutput, ILiquidStack lInput, IItemStack input) {
			this.output = output;
			this.lOutput = lOutput;
			this.lInput = lInput;
			this.input = input;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public String describe() {
			return "Adding canning of " + lInput.getDisplayName();
		}

		@Override
		public void apply() {
			RA.addFluidCannerRecipe(MineTweakerMC.getItemStack(input)
					, MineTweakerMC.getItemStack(output)
					, MineTweakerMC.getLiquidStack(lInput)
					, MineTweakerMC.getLiquidStack(lOutput));
		}
	}

	@ZenMethod
	public static void addRecipe(IItemStack output, ILiquidStack lOutput, IItemStack input, ILiquidStack lInput) {
		MineTweakerAPI.apply(new AddFluidCanningAction(output, lOutput, lInput, input));
	}
}
