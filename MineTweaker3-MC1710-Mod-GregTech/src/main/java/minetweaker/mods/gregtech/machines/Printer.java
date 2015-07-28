package minetweaker.mods.gregtech.machines;

import static gregtech.api.enums.GT_Values.RA;

import minetweaker.MineTweakerAPI;
import minetweaker.OneWayAction;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.gregtech.printer")
public class Printer {
	private static final class AddPrintingAction extends OneWayAction {
		private IItemStack output;
		private IItemStack input;
		private IItemStack special;
		private ILiquidStack ink;
		private int dur;
		private int euT;

		public AddPrintingAction(IItemStack output, IItemStack input, IItemStack special, ILiquidStack ink, int dur,
				int euT) {
					this.output = output;
					this.input = input;
					this.special = special;
					this.ink = ink;
					this.dur = dur;
					this.euT = euT;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public String describe() {
			return "Added printing of " + input.getDisplayName();
		}

		@Override
		public void apply() {
			RA.addPrinterRecipe(MineTweakerMC.getItemStack(input)
					, MineTweakerMC.getLiquidStack(ink)
					, MineTweakerMC.getItemStack(special)
					, MineTweakerMC.getItemStack(output)
					, dur, euT);
		}
	}

	@ZenMethod
	public static void addRecipe(IItemStack output, IItemStack input, IItemStack special, ILiquidStack ink, int dur, int euT) {
		MineTweakerAPI.apply(new AddPrintingAction(output, input, special, ink, dur, euT));
	}
}
