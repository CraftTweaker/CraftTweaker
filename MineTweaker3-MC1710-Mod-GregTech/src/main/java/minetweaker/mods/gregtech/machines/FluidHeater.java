package minetweaker.mods.gregtech.machines;

import static gregtech.api.enums.GT_Values.RA;

import minetweaker.MineTweakerAPI;
import minetweaker.OneWayAction;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.gregtech.FluidHeater")
public class FluidHeater {
	private static final class AddFluidHeatingAction extends OneWayAction {
		private ILiquidStack output;
		private ILiquidStack input;
		private IItemStack circuit;
		private int dur;
		private int euT;

		public AddFluidHeatingAction(ILiquidStack output, ILiquidStack input, IItemStack circuit, int dur, int euT) {
			this.output = output;
			this.input = input;
			this.circuit = circuit;
			this.dur = dur;
			this.euT = euT;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public String describe() {
			return "Adding heating of " + input.getDisplayName();
		}

		@Override
		public void apply() {
			RA.addFluidHeaterRecipe(MineTweakerMC.getItemStack(circuit)
					, MineTweakerMC.getLiquidStack(input)
					, MineTweakerMC.getLiquidStack(output)
					, dur, euT);
			
		}
	}

	@ZenMethod
	public static void addRecipe(ILiquidStack output, ILiquidStack input, IItemStack circuit, int dur, int euT) {
		MineTweakerAPI.apply(new AddFluidHeatingAction(output, input, circuit, dur, euT));
	}
}
