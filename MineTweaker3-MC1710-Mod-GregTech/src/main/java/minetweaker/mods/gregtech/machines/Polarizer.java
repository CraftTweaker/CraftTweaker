package minetweaker.mods.gregtech.machines;

import static gregtech.api.enums.GT_Values.RA;

import minetweaker.MineTweakerAPI;
import minetweaker.OneWayAction;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.gregtech.Polarizer")
public class Polarizer {

	private static final class AddPolarizationRecipeAction extends OneWayAction {
		private IItemStack output;
		private IItemStack input;
		private int dur;
		private int euT;

		public AddPolarizationRecipeAction(IItemStack output, IItemStack input, int dur, int euT) {
			this.output = output;
			this.input = input;
			this.dur = dur;
			this.euT = euT;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public String describe() {
			return "Adding polarization of " + input.getDisplayName() + " to " + output.getDisplayName();
		}

		@Override
		public void apply() {
			RA.addPolarizerRecipe(MineTweakerMC.getItemStack(input)
					, MineTweakerMC.getItemStack(output)
					, dur, euT);
		}
	}

	@ZenMethod
	public static void addRecipe(IItemStack output, IItemStack input, int dur, int euT) {
		MineTweakerAPI.apply(new AddPolarizationRecipeAction(output, input, dur, euT));
	}
}
