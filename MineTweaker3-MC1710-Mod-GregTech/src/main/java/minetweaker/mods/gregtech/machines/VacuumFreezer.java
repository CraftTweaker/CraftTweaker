package minetweaker.mods.gregtech.machines;

import gregtech.api.GregTech_API;
import minetweaker.MineTweakerAPI;
import minetweaker.OneWayAction;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * Provides access to the Vacuum Freezer recipes.
 * 
 * @author Stan Hebben
 */
@ZenClass("mods.gregtech.VacuumFreezer")
@ModOnly("gregtech")
public class VacuumFreezer {
	/**
	 * Adds a vacuum freezer recipe.
	 * 
	 * @param output recipe output
	 * @param input recipe input
	 * @param durationTicks freezing duration, in ticks
	 */
	@ZenMethod
	public static void addRecipe(IItemStack output, IItemStack input, int durationTicks) {
		MineTweakerAPI.apply(new AddRecipeAction(output, input, durationTicks));
	}
	
	// ######################
	// ### Action classes ###
	// ######################
	
	private static class AddRecipeAction extends OneWayAction {
		private final IItemStack output;
		private final IItemStack input;
		private final int duration;
		
		public AddRecipeAction(IItemStack output, IItemStack input, int duration) {
			this.output = output;
			this.input = input;
			this.duration = duration;
		}

		@Override
		public void apply() {
			GregTech_API.sRecipeAdder.addVacuumFreezerRecipe(
					MineTweakerMC.getItemStack(input),
					MineTweakerMC.getItemStack(output),
					duration);
		}

		@Override
		public String describe() {
			return "Adding vacuum freezer recipe for " + output;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public int hashCode() {
			int hash = 5;
			hash = 67 * hash + (this.output != null ? this.output.hashCode() : 0);
			hash = 67 * hash + (this.input != null ? this.input.hashCode() : 0);
			hash = 67 * hash + this.duration;
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
			if (this.output != other.output && (this.output == null || !this.output.equals(other.output))) {
				return false;
			}
			if (this.input != other.input && (this.input == null || !this.input.equals(other.input))) {
				return false;
			}
			if (this.duration != other.duration) {
				return false;
			}
			return true;
		}
	}
}
