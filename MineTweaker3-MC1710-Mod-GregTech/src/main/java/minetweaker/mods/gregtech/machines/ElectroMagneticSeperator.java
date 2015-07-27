package minetweaker.mods.gregtech.machines;

import static gregtech.api.enums.GT_Values.RA;

import minetweaker.MineTweakerAPI;
import minetweaker.OneWayAction;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.gregtech.ElectroMagneticSeperator")
public class ElectroMagneticSeperator {
	private static final class AddElectromagneticSeperationAction extends OneWayAction {
		private IItemStack output1;
		private IItemStack output2;
		private IItemStack output3;
		private IItemStack input;
		private int[] chances;
		private int dur;
		private int euT;

		public AddElectromagneticSeperationAction(IItemStack output1, IItemStack output2, IItemStack output3,
				IItemStack input, int[] chances, int dur, int euT) {
					this.output1 = output1;
					this.output2 = output2;
					this.output3 = output3;
					this.input = input;
					this.chances = chances;
					this.dur = dur;
					this.euT = euT;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public String describe() {
			return "Adding electro-magnetic seperation of " + input.getDisplayName();
		}

		@Override
		public void apply() {
			RA.addElectromagneticSeparatorRecipe(MineTweakerMC.getItemStack(input)
					, MineTweakerMC.getItemStack(output1)
					, MineTweakerMC.getItemStack(output2)
					, MineTweakerMC.getItemStack(output3)
					, chances, dur, euT);
		}
	}

	@ZenMethod
	public static void addRecipe(IItemStack output1, IItemStack output2, IItemStack output3, IItemStack input, int[] chances,
			int dur, int euT) {
		MineTweakerAPI.apply(new AddElectromagneticSeperationAction(output1, output2, output3, input, chances, dur, euT));
	}
}
