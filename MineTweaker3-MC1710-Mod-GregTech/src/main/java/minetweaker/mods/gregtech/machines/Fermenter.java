package minetweaker.mods.gregtech.machines;

import static gregtech.api.enums.GT_Values.RA;

import minetweaker.MineTweakerAPI;
import minetweaker.OneWayAction;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.gregtech.Fermenter")
public class Fermenter {
	private static final class AddFermentationAction extends OneWayAction {
		private ILiquidStack input;
		private ILiquidStack output;
		private int dur;
		private boolean hidden;

		public AddFermentationAction(ILiquidStack input, ILiquidStack output, int dur, boolean hidden) {
			this.input = input;
			this.output = output;
			this.dur = dur;
			this.hidden = hidden;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public String describe() {
			return "Adding fermentation of " + input.getDisplayName();
		}

		@Override
		public void apply() {
			RA.addFermentingRecipe(MineTweakerMC.getLiquidStack(input)
					, MineTweakerMC.getLiquidStack(output)
					, dur, hidden);
		}
	}

	@ZenMethod
	public static void addRecipe(ILiquidStack input, ILiquidStack output, int dur, boolean hidden) {
		MineTweakerAPI.apply(new AddFermentationAction(input, output, dur, hidden));
	}
}
