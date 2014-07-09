package minetweaker.mods.gregtech.machines;

import gregtechmod.api.GregTech_API;
import java.util.Arrays;
import minetweaker.MineTweakerAPI;
import minetweaker.OneWayAction;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * Provides access to the distillation tower recipes.
 * 
 * @author Stan Hebben
 */
@ZenClass("mods.gregtech.DistillationTower")
@ModOnly("gregtech_addon")
public class DistillationTower {
	/**
	 * Adds a distillation tower recipe with a single output.
	 * 
	 * @param output recipe output
	 * @param input recipe input
	 * @param cells number of cells required (can be 0)
	 * @param durationTicks distillation time, in ticks
	 * @param euPerTick eu consumption per tick
	 */
	@ZenMethod
	public static void addRecipe(IItemStack output, IItemStack input, int cells, int durationTicks, int euPerTick) {
		MineTweakerAPI.apply(new AddRecipeAction(new IItemStack[] { output }, input, cells, durationTicks, euPerTick));
	}
	
	/**
	 * Adds a distillation tower recipe with multiple outputs.
	 * 
	 * @param outputs array with 1-4 outputs
	 * @param input recipe input
	 * @param cells number of cells required (can be 0)
	 * @param durationTicks distillation time, in ticks
	 * @param euPerTick eu consumption per tick
	 */
	@ZenMethod
	public static void addRecipe(IItemStack[] outputs, IItemStack input, int cells, int durationTicks, int euPerTick) {
		if (outputs.length == 0) {
			MineTweakerAPI.logError("Distillation tower recipe requires at least 1 output");
		} else {
			MineTweakerAPI.apply(new AddRecipeAction(outputs, input, cells, durationTicks, euPerTick));
		}
	}
	
	// ######################
	// ### Action classes ###
	// ######################
	
	private static class AddRecipeAction extends OneWayAction {
		private final IItemStack[] outputs;
		private final IItemStack input;
		private final int cells;
		private final int duration;
		private final int euPerTick;
		
		public AddRecipeAction(IItemStack[] outputs, IItemStack input, int cells, int duration, int euPerTick) {
			this.outputs = outputs;
			this.input = input;
			this.cells = cells;
			this.duration = duration;
			this.euPerTick = euPerTick;
		}

		@Override
		public void apply() {
			GregTech_API.sRecipeAdder.addDistillationRecipe(
					MineTweakerMC.getItemStack(input),
					cells,
					MineTweakerMC.getItemStack(outputs[0]),
					outputs.length > 1 ? MineTweakerMC.getItemStack(outputs[1]) : null,
					outputs.length > 2 ? MineTweakerMC.getItemStack(outputs[2]) : null,
					outputs.length > 3 ? MineTweakerMC.getItemStack(outputs[3]) : null,
					duration, euPerTick);
		}

		@Override
		public String describe() {
			return "Adding Distillation tower recipe for " + outputs[0];
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public int hashCode() {
			int hash = 3;
			hash = 41 * hash + Arrays.deepHashCode(this.outputs);
			hash = 41 * hash + (this.input != null ? this.input.hashCode() : 0);
			hash = 41 * hash + this.cells;
			hash = 41 * hash + this.duration;
			hash = 41 * hash + this.euPerTick;
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
			if (!Arrays.deepEquals(this.outputs, other.outputs)) {
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
			if (this.euPerTick != other.euPerTick) {
				return false;
			}
			return true;
		}
	}
}
