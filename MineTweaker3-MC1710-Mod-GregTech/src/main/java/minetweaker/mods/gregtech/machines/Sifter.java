package minetweaker.mods.gregtech.machines;

import static gregtech.api.enums.GT_Values.RA;

import minetweaker.MineTweakerAPI;
import minetweaker.OneWayAction;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.gregtech.Sifter")
public class Sifter {
	private static final class AddSiftingAction extends OneWayAction {
		private IItemStack[] outputs;
		private IItemStack input;
		private int[] chances;
		private int dur;
		private int euT;

		public AddSiftingAction(IItemStack[] outputs, IItemStack input, int[] chances, int dur, int euT) {
			this.outputs = outputs;
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
			return "Added sifting of " + input.getDisplayName();
		}

		@Override
		public void apply() {
			RA.addSifterRecipe(MineTweakerMC.getItemStack(input)
					, MineTweakerMC.getItemStacks(outputs)
					, chances, dur, euT);
		}
	}

	@ZenMethod
	public static void addRecipe(IItemStack[] outputs, IItemStack input, int[] chances, int dur, int euT) {
		MineTweakerAPI.apply(new AddSiftingAction(outputs, input, chances, dur, euT));
	}
}
