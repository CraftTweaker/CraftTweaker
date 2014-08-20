package minetweaker.mods.gregtech.machines;

import gregtech.api.GregTech_API;
import static gregtech.api.GregTech_API.MOD_ID;
import minetweaker.MineTweakerAPI;
import minetweaker.OneWayAction;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * Provides access to the Chemical Reactor recipes.
 * 
 * @author Stan Hebben
 */
@ZenClass("mods.gregtech.ChemicalBath")
@ModOnly(MOD_ID)
public class ChemicalBath {
	/**
	 * Adds a Chemical Reactor recipe.
	 * 
	 * @param output recipe output
	 * @param input1 primary input
	 * @param input2 secondary input
	 * @param durationTicks reaction time, in ticks
	 */
	@ZenMethod
	public static void addRecipe(IItemStack output, IItemStack input1, IItemStack input2, int durationTicks) {
		MineTweakerAPI.apply(new AddRecipeAction(output, input1, input2, durationTicks));
	}
	
	// ######################
	// ### Action classes ###
	// ######################
	
	private static class AddRecipeAction extends OneWayAction {
		private final IItemStack output;
		private final IItemStack input1;
		private final IItemStack input2;
		private final int duration;
		
		public AddRecipeAction(IItemStack output, IItemStack input1, IItemStack input2, int duration) {
			this.output = output;
			this.input1 = input1;
			this.input2 = input2;
			this.duration = duration;
		}

		@Override
		public void apply() {
			GregTech_API.sRecipeAdder.addChemicalRecipe(
					MineTweakerMC.getItemStack(input1),
					MineTweakerMC.getItemStack(input2),
					MineTweakerMC.getItemStack(output),
					duration);
		}

		@Override
		public String describe() {
			return "Adding chemical reactor recipe for " + output;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public int hashCode() {
			int hash = 7;
			hash = 11 * hash + (this.output != null ? this.output.hashCode() : 0);
			hash = 11 * hash + (this.input1 != null ? this.input1.hashCode() : 0);
			hash = 11 * hash + (this.input2 != null ? this.input2.hashCode() : 0);
			hash = 11 * hash + this.duration;
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
			if (this.input1 != other.input1 && (this.input1 == null || !this.input1.equals(other.input1))) {
				return false;
			}
			if (this.input2 != other.input2 && (this.input2 == null || !this.input2.equals(other.input2))) {
				return false;
			}
			if (this.duration != other.duration) {
				return false;
			}
			return true;
		}
	}
}
