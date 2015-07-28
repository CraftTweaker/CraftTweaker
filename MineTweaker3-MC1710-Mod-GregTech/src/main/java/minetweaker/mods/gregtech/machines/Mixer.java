package minetweaker.mods.gregtech.machines;

import static gregtech.api.enums.GT_Values.RA;

import minetweaker.MineTweakerAPI;
import minetweaker.OneWayAction;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.gregtech.Mixer")
public class Mixer {
	private static final class AddMixingAction extends OneWayAction {
		private IItemStack output;
		private ILiquidStack lOutput;
		private IItemStack input1;
		private IItemStack input2;
		private IItemStack input3;
		private IItemStack input4;
		private int dur;
		private int euT;
		private ILiquidStack lInput;

		public AddMixingAction(IItemStack output, ILiquidStack lOutput, ILiquidStack lInput, IItemStack input1, IItemStack input2,
				IItemStack input3, IItemStack input4, int dur, int euT) {
					this.output = output;
					this.lOutput = lOutput;
					this.lInput = lInput;
					this.input1 = input1;
					this.input2 = input2;
					this.input3 = input3;
					this.input4 = input4;
					this.dur = dur;
					this.euT = euT;					
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public String describe() {
			return "Adding mixing w/ multiple inputs";
		}

		@Override
		public void apply() {
			RA.addMixerRecipe(MineTweakerMC.getItemStack(input1)
					, MineTweakerMC.getItemStack(input2)
					, MineTweakerMC.getItemStack(input3)
					, MineTweakerMC.getItemStack(input4)
					, MineTweakerMC.getLiquidStack(lInput)
					, MineTweakerMC.getLiquidStack(lOutput)
					, MineTweakerMC.getItemStack(output)
					, dur, euT);
		}
	}

	@ZenMethod
	public static void addRecipe(IItemStack output, ILiquidStack lOutput, ILiquidStack lInput,  IItemStack input1, IItemStack input2, 
			IItemStack input3, IItemStack input4, int dur, int euT) {
		MineTweakerAPI.apply(new AddMixingAction(output, lOutput, lInput, input1, input2, input3, input4, dur, euT));
	}
	
}
