package minetweaker.mods.gregtech.machines;

import static gregtech.api.enums.GT_Values.RA;

import minetweaker.MineTweakerAPI;
import minetweaker.OneWayAction;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.gregtech.Slicer")
public class Slicer {
	private static final class AddSlicingRecipeAction extends OneWayAction {
		private IItemStack input;
		private IItemStack shape;
		private IItemStack output;
		private int dur;
		private int euT;

		public AddSlicingRecipeAction(IItemStack input, IItemStack shape, IItemStack output, int dur, int euT) {
			this.input = input;
			this.shape = shape;
			this.output = output;
			this.dur = dur;
			this.euT = euT;			
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public String describe() {
			return "Adding slicing of " + input.getDisplayName() + " into " + output.getDisplayName();
		}

		@Override
		public void apply() {
			RA.addSlicerRecipe(MineTweakerMC.getItemStack(input)
					, MineTweakerMC.getItemStack(shape)
					, MineTweakerMC.getItemStack(output)
					, dur, euT);
		}
	}

	@ZenMethod
	public static void addRecipe(IItemStack input, IItemStack shape, IItemStack output, int dur, int euT) {
		MineTweakerAPI.apply(new AddSlicingRecipeAction(input, shape, output, dur, euT));
	}
}
