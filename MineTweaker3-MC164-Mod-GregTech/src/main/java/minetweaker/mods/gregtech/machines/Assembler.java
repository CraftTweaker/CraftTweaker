package minetweaker.mods.gregtech.machines;

import gregtechmod.api.GregTech_API;
import minetweaker.MineTweakerAPI;
import minetweaker.OneWayAction;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * Provides access to the assembler recipes.
 * 
 * @author Stan Hebben
 */
@ZenClass("mods.gregtech.Assembler")
@ModOnly("gregtech_addon")
public class Assembler {
	/**
	 * Adds an assemble recipe.
	 * 
	 * @param output recipe output
	 * @param input1 primary input
	 * @param input2 secondary input (optional, can be null)
	 * @param durationTicks assembling duration, in ticks
	 * @param euPerTick eu consumption per tick
	 */
	@ZenMethod
	public static void addRecipe(IItemStack output, IItemStack input1, IItemStack input2, int durationTicks, int euPerTick) {
		MineTweakerAPI.apply(new AddRecipeAction(output, input1, input2, durationTicks, euPerTick));
	}
	
	// ######################
	// ### Action classes ###
	// ######################
	
	private static class AddRecipeAction extends OneWayAction {
		private final IItemStack output;
		private final IItemStack input1;
		private final IItemStack input2;
		private final int duration;
		private final int euPerTick;
		
		public AddRecipeAction(IItemStack output, IItemStack input1, IItemStack input2, int duration, int euPerTick) {
			this.output = output;
			this.input1 = input1;
			this.input2 = input2;
			this.duration = duration;
			this.euPerTick = euPerTick;
		}

		@Override
		public void apply() {
			GregTech_API.sRecipeAdder.addAssemblerRecipe(
					MineTweakerMC.getItemStack(input1),
					MineTweakerMC.getItemStack(input2),
					MineTweakerMC.getItemStack(output),
					duration,
					euPerTick);
		}

		@Override
		public String describe() {
			return "Adding assembler recipe for " + output;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public int hashCode() {
			int hash = 7;
			hash = 79 * hash + (this.output != null ? this.output.hashCode() : 0);
			hash = 79 * hash + (this.input1 != null ? this.input1.hashCode() : 0);
			hash = 79 * hash + (this.input2 != null ? this.input2.hashCode() : 0);
			hash = 79 * hash + this.duration;
			hash = 79 * hash + this.euPerTick;
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
			if (this.euPerTick != other.euPerTick) {
				return false;
			}
			return true;
		}
	}
}
