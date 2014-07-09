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
 * Provider access to the Canner recipes.
 * 
 * @author Stan Hebben
 */
@ZenClass("mods.gregtech.Canner")
@ModOnly("gregtech_addon")
public class Canner {
	/**
	 * Adds a canner recipe with a single output.
	 * 
	 * @param output crafting output
	 * @param input1 primary input
	 * @param input2 secondary input (optional
	 * @param durationTicks crafting duration, in ticks
	 * @param euPerTick eu consumption per tick
	 */
	@ZenMethod
	public static void addRecipe(IItemStack output, IItemStack input1, IItemStack input2, int durationTicks, int euPerTick) {
		MineTweakerAPI.apply(new AddRecipeAction(output, null, input1, input2, durationTicks, euPerTick));
	}
	
	/**
	 * Adds a canner recipe with multiple outputs.
	 * 
	 * @param output array with 1 or 2 outputs
	 * @param input1 primary input
	 * @param input2 secondary input (optional, can be null)
	 * @param durationTicks crafting duration, in ticks
 	 * @param euPerTick eu consumption per tick
	 */
	@ZenMethod
	public static void addRecipe(IItemStack[] output, IItemStack input1, IItemStack input2, int durationTicks, int euPerTick) {
		if (output.length == 0) {
			MineTweakerAPI.logError("canner requires at least 1 output");
		} else {
			MineTweakerAPI.apply(new AddRecipeAction(output[0], output.length > 1 ? output[1] : null, input1, input2, durationTicks, euPerTick));
		}
	}
	
	// ######################
	// ### Action classes ###
	// ######################
	
	private static class AddRecipeAction extends OneWayAction {
		private final IItemStack output1;
		private final IItemStack output2;
		private final IItemStack input1;
		private final IItemStack input2;
		private final int duration;
		private final int euPerTick;
		
		public AddRecipeAction(IItemStack output1, IItemStack output2, IItemStack input1, IItemStack input2, int duration, int euPerTick) {
			this.output1 = output1;
			this.output2 = output2;
			this.input1 = input1;
			this.input2 = input2;
			this.duration = duration;
			this.euPerTick = euPerTick;
		}

		@Override
		public void apply() {
			GregTech_API.sRecipeAdder.addCannerRecipe(
					MineTweakerMC.getItemStack(input1),
					MineTweakerMC.getItemStack(input2),
					MineTweakerMC.getItemStack(output1),
					MineTweakerMC.getItemStack(output2),
					duration,
					euPerTick);
		}

		@Override
		public String describe() {
			return "Adding canner recipe for " + output1;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public int hashCode() {
			int hash = 7;
			hash = 79 * hash + (this.output1 != null ? this.output1.hashCode() : 0);
			hash = 79 * hash + (this.output2 != null ? this.output2.hashCode() : 0);
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
			if (this.output1 != other.output1 && (this.output1 == null || !this.output1.equals(other.output1))) {
				return false;
			}
			if (this.output2 != other.output2 && (this.output2 == null || !this.output2.equals(other.output2))) {
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
