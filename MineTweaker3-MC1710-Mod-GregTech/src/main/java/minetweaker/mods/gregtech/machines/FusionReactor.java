package minetweaker.mods.gregtech.machines;

import static gregtech.api.enums.GT_Values.RA;

import minetweaker.MineTweakerAPI;
import minetweaker.OneWayAction;
import minetweaker.annotations.ModOnly;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * Provides access to the fusion reactor recipes.
 * 
 * @author Stan Hebben (rewritten by Ben)
 */
@ZenClass("mods.gregtech.FusionReactor")
@ModOnly("gregtech")
public class FusionReactor {
	/**
	 * Action to add a recipe to the fusion reactor
	 * @author ben
	 *
	 */
	private static final class AddFusionReactionAction extends OneWayAction {
		private ILiquidStack output;
		private ILiquidStack input1;
		private ILiquidStack input2;
		private int durationTicks;
		private int energyPerTick;
		private int startEnergy;

		public AddFusionReactionAction(ILiquidStack output, ILiquidStack input1, ILiquidStack input2, int durationTicks,
				int energyPerTick, int startEnergy) {
					this.output = output;
			// TODO Auto-generated constructor stub
					this.input1 = input1;
					this.input2 = input2;
					this.durationTicks = durationTicks;
					this.energyPerTick = energyPerTick;
					this.startEnergy = startEnergy;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public String describe() {
			return "Adding fusion recipe for " + output.getName();
		}

		@Override
		public void apply() {
			RA.addFusionReactorRecipe(MineTweakerMC.getLiquidStack(input1), MineTweakerMC.getLiquidStack(input2), MineTweakerMC.getLiquidStack(output)
					, durationTicks, energyPerTick, startEnergy);
		}
	}

	/**
	 * Adds a Fusion Reactor recipe.
	 * 
	 * @param input1 = first Input (not null, and respects StackSize)
	 * @param input2= second Input (not null, and respects StackSize)
	 * @param output = Output of the Fusion (can be null, and respects StackSize)
	 * @param durationInTicks = How many ticks the Fusion lasts (must be > 0)
	 * @param energyPerTick = The EU generated per Tick (can even be negative!)
	 * @param startEnergy = EU needed for heating the Reactor up (must be >= 0)
	 */
	@ZenMethod
	public static void addRecipe(ILiquidStack output, ILiquidStack input1, ILiquidStack input2, int durationTicks, int energyPerTick, int startEnergy) {
		MineTweakerAPI.apply(new AddFusionReactionAction(output, input1, input2, durationTicks, energyPerTick, startEnergy));
	}
	
	
}
