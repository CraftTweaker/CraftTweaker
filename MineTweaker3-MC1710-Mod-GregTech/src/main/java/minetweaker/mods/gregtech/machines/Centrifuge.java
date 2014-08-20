package minetweaker.mods.gregtech.machines;

import gregtech.api.GregTech_API;
import static gregtech.api.GregTech_API.MOD_ID;
import java.util.Arrays;
import minetweaker.MineTweakerAPI;
import minetweaker.OneWayAction;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author Stan
 */
@ZenClass("mods.gregtech.Centrifuge")
@ModOnly(MOD_ID)
public class Centrifuge {
	/**
	 * Adds a centrifuge recipe.
	 * 
	 * @param outputs array with 1-6 outputs
	 * @param input recipe input
	 * @param cells number of cells required, can be 0
	 * @param durationTicks centrifuging time, in ticks
	 */
	@ZenMethod
	public static void addRecipe(IItemStack[] outputs, IItemStack input, int cells, int durationTicks) {
		if (outputs.length < 1) {
			MineTweakerAPI.logError("centrifuge must have at least 1 output");
		} else {
			MineTweakerAPI.apply(new AddRecipeAction(outputs, input, cells, durationTicks));
		}
	}
	
	/**
	 * Adds a centrifuge recipe taking a fuel can as container.
	 * 
	 * @param outputs array with 1-6 outputs
	 * @param input recipe input
	 * @param duration centrifuging time, in ticks
	 */
	@ZenMethod
	public static void addRecipeFuelCan(IItemStack[] outputs, IItemStack input, int duration) {
		if (outputs.length < 1) {
			MineTweakerAPI.logError("centrifuge must have at least 1 output");
		} else {
			MineTweakerAPI.apply(new AddRecipeAction(outputs, input, -1, duration));
		}
	}
	
	// ######################
	// ### Action classes ###
	// ######################
	
	private static class AddRecipeAction extends OneWayAction {
		private final IItemStack[] output;
		private final IItemStack input;
		private final int cells;
		private final int duration;
		
		public AddRecipeAction(IItemStack[] output, IItemStack input, int cells, int duration) {
			this.output = output;
			this.input = input;
			this.cells = cells;
			this.duration = duration;
		}

		@Override
		public void apply() {
			GregTech_API.sRecipeAdder.addCentrifugeRecipe(
					MineTweakerMC.getItemStack(input),
					cells,
					MineTweakerMC.getItemStack(output[0]),
					output.length > 1 ? MineTweakerMC.getItemStack(output[1]) : null,
					output.length > 2 ? MineTweakerMC.getItemStack(output[2]) : null,
					output.length > 3 ? MineTweakerMC.getItemStack(output[3]) : null,
					output.length > 4 ? MineTweakerMC.getItemStack(output[4]) : null,
					output.length > 5 ? MineTweakerMC.getItemStack(output[5]) : null,
					duration);
		}

		@Override
		public String describe() {
			return "Adding centrifuge recipe with input " + input;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public int hashCode() {
			int hash = 7;
			hash = 59 * hash + Arrays.deepHashCode(this.output);
			hash = 59 * hash + (this.input != null ? this.input.hashCode() : 0);
			hash = 59 * hash + this.cells;
			hash = 59 * hash + this.duration;
			return hash;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final AddRecipeAction other = (AddRecipeAction) obj;
			if (!Arrays.deepEquals(this.output, other.output)) {
				return false;
			}
			if (this.input != other.input && (this.input == null || !this.input.equals(other.input))) {
				return false;
			}
			if (this.cells != other.cells) {
				return false;
			}
			if (this.duration != other.duration) {
				return false;
			}
			return true;
		}
	}
}
