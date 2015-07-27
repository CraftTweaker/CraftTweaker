package minetweaker.mods.gregtech.machines;

import static gregtech.api.enums.GT_Values.RA;

import minetweaker.MineTweakerAPI;
import minetweaker.OneWayAction;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.gregtech.Cutter")
public class Cutter {
	private static final class AddCutterRecipeAction extends OneWayAction {
		private IItemStack output1;
		private IItemStack output2;
		private ILiquidStack lubricant;
		private IItemStack input;
		private int dur;
		private int euT;

		public AddCutterRecipeAction(IItemStack output1, IItemStack output2, ILiquidStack lubricant, IItemStack input,
				int dur, int euT) {
					this.output1 = output1;
					this.output2 = output2;
					this.lubricant = lubricant;
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
			return "Adding cutting action for " + input.getDisplayName();
		}
		
		@Override
		public void apply() {
			if(lubricant == null) {
				RA.addCutterRecipe(MineTweakerMC.getItemStack(input)
						, MineTweakerMC.getItemStack(output1)
						, MineTweakerMC.getItemStack(output2)
						, dur, euT);
			} else {
				RA.addCutterRecipe(MineTweakerMC.getItemStack(input)
						, MineTweakerMC.getLiquidStack(lubricant)
						, MineTweakerMC.getItemStack(output1)
						, MineTweakerMC.getItemStack(output2)
						, dur, euT);
			}
		}
	}

	@ZenMethod
	public static void addRecipe(IItemStack output1, IItemStack output2, ILiquidStack lubricant, 
			IItemStack input, int dur,int euT) {
		MineTweakerAPI.apply(new AddCutterRecipeAction(output1, output2, lubricant, input, dur, euT));
	}
}
