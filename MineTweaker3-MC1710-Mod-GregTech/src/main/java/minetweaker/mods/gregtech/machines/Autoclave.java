package minetweaker.mods.gregtech.machines;

import static gregtech.api.enums.GT_Values.RA;

import minetweaker.MineTweakerAPI;
import minetweaker.OneWayAction;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.gregtech.Autoclave")
public class Autoclave {
	private static final class AddAutoclavingAction extends OneWayAction {
		private IItemStack output;
		private IItemStack input;
		private ILiquidStack lInput;
		private int chance;
		private int dur;
		private int euT;

		public AddAutoclavingAction(IItemStack output, IItemStack input, ILiquidStack lInput, int chance, int dur,
				int euT) {
					this.output = output;
					this.input = input;
					this.lInput = lInput;
					this.chance = chance;
					this.dur = dur;
					this.euT = euT;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public String describe() {
			return "Adding autoclaving for " + input.getDisplayName();
		}

		@Override
		public void apply() {
			RA.addAutoclaveRecipe(MineTweakerMC.getItemStack(input)
					, MineTweakerMC.getLiquidStack(lInput)
					, MineTweakerMC.getItemStack(output)
					, chance, dur, euT);
		}
	}

	@ZenMethod
	public static void addRecipe(IItemStack output, IItemStack input, ILiquidStack lInput, int chance, int dur, int euT) {
		MineTweakerAPI.apply(new AddAutoclavingAction(output, input, lInput, chance, dur, euT));
	}
}
