package minetweaker.mods.gregtech.machines;

import static gregtech.api.enums.GT_Values.RA;

import minetweaker.MineTweakerAPI;
import minetweaker.OneWayAction;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.gregtech.ArcFurnace")
public class ArcFurnace {
	private static final class AddRecipeAction extends OneWayAction {
		private IItemStack[] outputs;
		private IItemStack input;
		private ILiquidStack lInput;
		private int[] chances;
		private int dur;
		private int euT;
		private boolean reqPlasma;

		public AddRecipeAction(IItemStack[] outputs, IItemStack input, ILiquidStack lInput, int[] chances, int dur, int euT, boolean reqPlasma) {
			this.outputs = outputs;
			this.input = input;
			this.lInput = lInput;
			this.chances = chances;
			this.dur = dur;
			this.euT = euT;
			this.reqPlasma = reqPlasma;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public String describe() {
			return "Adding " + input.getDisplayName() + " to Arc Furnace";
		}

		@Override
		public void apply() {
			if(reqPlasma) {
				RA.addSimpleArcFurnaceRecipe(MineTweakerMC.getItemStack(input)
						, MineTweakerMC.getLiquidStack(lInput)
						, MineTweakerMC.getItemStacks(outputs)
						, chances, dur, euT);
			} else {
				RA.addPlasmaArcFurnaceRecipe(MineTweakerMC.getItemStack(input)
						, MineTweakerMC.getLiquidStack(lInput)
						, MineTweakerMC.getItemStacks(outputs)
						, chances, dur, euT);
			}
		}
	}

	@ZenMethod
	public static void addRecipe(IItemStack[] outputs, IItemStack input, ILiquidStack lInput, int[] chances, int dur, int euT, boolean reqPlasma) {
		MineTweakerAPI.apply(new AddRecipeAction(outputs, input, lInput, chances, dur, euT, reqPlasma));
	}
}
