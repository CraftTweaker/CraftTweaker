package minetweaker.mods.gregtech.machines;

import static gregtech.api.enums.GT_Values.RA;

import minetweaker.MineTweakerAPI;
import minetweaker.OneWayAction;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.gregtech.FormingPress")
public class FormingPress {
	private static final class AddPressFormingAction extends OneWayAction {
		private IItemStack output;
		private IItemStack input;
		private IItemStack form;
		private int dur;
		private int euT;

		public AddPressFormingAction(IItemStack output, IItemStack input, IItemStack form, int dur, int euT) {
			this.output = output;
			this.input = input;
			this.form = form;
			this.dur = dur;
			this.euT = euT;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public String describe() {
			return "Added press forming of " + input.getDisplayName();
		}

		@Override
		public void apply() {
			RA.addFormingPressRecipe(MineTweakerMC.getItemStack(input)
					, MineTweakerMC.getItemStack(form)
					, MineTweakerMC.getItemStack(output)
					, dur, euT);
		}
	}

	@ZenMethod
	public static void addRecipe(IItemStack output, IItemStack input, IItemStack form, int dur, int euT) {
		MineTweakerAPI.apply(new AddPressFormingAction(output, input, form, dur, euT));
	}
}
