package minetweaker.mods.gregtech.machines;

import static gregtech.api.enums.GT_Values.RA;

import minetweaker.MineTweakerAPI;
import minetweaker.OneWayAction;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.gregtech.FluidSmelter")
public class FluidSmelter {
	private static final class AddFluidSmeltingAction extends OneWayAction {
		private ILiquidStack output;
		private IItemStack input;
		private IItemStack remains;
		private int chance;
		private int dur;
		private int euT;

		public AddFluidSmeltingAction(ILiquidStack output, IItemStack input, IItemStack remains, int chance, int dur,
				int euT) {
					this.output = output;
					this.input = input;
					this.remains = remains;
					this.chance = chance;
					this.dur = dur;
					this.euT = euT;
		}

		@Override
		public void apply() {
			RA.addFluidSmelterRecipe(MineTweakerMC.getItemStack(input)
					, MineTweakerMC.getItemStack(remains)
					, MineTweakerMC.getLiquidStack(output)
					, chance, dur, euT);
		}

		@Override
		public String describe() {
			return "Adding fluid smelting for " + input.getDisplayName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}

	@ZenMethod
	public static void addRecipe(ILiquidStack output, IItemStack input, IItemStack remains, int chance, int dur, int euT) {
		MineTweakerAPI.apply(new AddFluidSmeltingAction(output, input, remains, chance, dur, euT));
	}
}
