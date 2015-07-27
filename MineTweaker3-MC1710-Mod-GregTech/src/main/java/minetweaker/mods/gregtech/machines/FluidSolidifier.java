package minetweaker.mods.gregtech.machines;

import static gregtech.api.enums.GT_Values.RA;

import minetweaker.MineTweakerAPI;
import minetweaker.OneWayAction;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.gregtech.FluidSolidifier")
public class FluidSolidifier {
	private static final class AddFluidSolidificationAction extends OneWayAction {
		private IItemStack output;
		private ILiquidStack input;
		private IItemStack mold;
		private int dur;
		private int euT;

		public AddFluidSolidificationAction(IItemStack output, ILiquidStack input, IItemStack mold, int dur, int euT) {
			this.output = output;
			this.input = input;
			this.mold = mold;
			this.dur = dur;
			this.euT = euT;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public String describe() {
			return "Adding solidification of fluid " + input.getDisplayName();
		}

		@Override
		public void apply() {
			RA.addFluidSolidifierRecipe(MineTweakerMC.getItemStack(mold)
					, MineTweakerMC.getLiquidStack(input)
					, MineTweakerMC.getItemStack(output)
					, dur, euT);
		}
	}

	@ZenMethod
	public static void addRecipe(IItemStack output, ILiquidStack input, IItemStack mold, int dur, int euT) {
		MineTweakerAPI.apply(new AddFluidSolidificationAction(output, input, mold, dur, euT));
	}
}
