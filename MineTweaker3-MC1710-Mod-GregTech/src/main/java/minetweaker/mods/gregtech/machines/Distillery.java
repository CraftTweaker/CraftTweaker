package minetweaker.mods.gregtech.machines;

import static gregtech.api.enums.GT_Values.RA;

import minetweaker.MineTweakerAPI;
import minetweaker.OneWayAction;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.gregtech.Distillery")
public class Distillery {
	private static final class AddDistillationAction extends OneWayAction {
		private ILiquidStack output;
		private ILiquidStack input;
		private IItemStack circuit;
		private int dur;
		private int euT;
		private boolean hidden;

		public AddDistillationAction(ILiquidStack output, ILiquidStack input, IItemStack circuit,
				int dur, int euT, boolean hidden) {
					this.output = output;
					this.input = input;
					this.circuit = circuit;
					this.dur = dur;
					this.euT = euT;
					this.hidden = hidden;					
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public String describe() {
			return "Adding distillation of " + input.getDisplayName();
		}

		@Override
		public void apply() {
			RA.addDistilleryRecipe(MineTweakerMC.getItemStack(circuit)
					, MineTweakerMC.getLiquidStack(input)
					, MineTweakerMC.getLiquidStack(output)
					, dur, euT, hidden);
		}
	}

	@ZenMethod
	public static void addRecipe(ILiquidStack output, ILiquidStack input, IItemStack circuit, int dur, int euT, boolean hidden) {
		MineTweakerAPI.apply(new AddDistillationAction(output, input, circuit, dur, euT, hidden));
	}
}
