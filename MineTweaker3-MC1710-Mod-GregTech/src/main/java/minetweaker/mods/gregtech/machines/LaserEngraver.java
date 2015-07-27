package minetweaker.mods.gregtech.machines;

import static gregtech.api.enums.GT_Values.RA;

import minetweaker.MineTweakerAPI;
import minetweaker.OneWayAction;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.gregtech.LaserEngraver")
public class LaserEngraver {
	private static final class AddLaserEngravingAction extends OneWayAction {
		private IItemStack output;
		private IItemStack input;
		private IItemStack lens;
		private int dur;
		private int euT;

		public AddLaserEngravingAction(IItemStack output, IItemStack input, IItemStack lens, int dur, int euT) {
			this.output = output;
			this.input = input;
			this.lens = lens;
			this.dur = dur;
			this.euT = euT;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public String describe() {
			return "Added laser engraving of " + input.getDisplayName();
		}

		@Override
		public void apply() {
			RA.addLaserEngraverRecipe(MineTweakerMC.getItemStack(input)
					, MineTweakerMC.getItemStack(lens)
					, MineTweakerMC.getItemStack(output)
					, dur, euT);
		}
	}

	@ZenMethod
	public static void addRecipe(IItemStack output, IItemStack input, IItemStack lens, int dur, int euT) {
		MineTweakerAPI.apply(new AddLaserEngravingAction(output, input, lens, dur, euT));
	}
}
