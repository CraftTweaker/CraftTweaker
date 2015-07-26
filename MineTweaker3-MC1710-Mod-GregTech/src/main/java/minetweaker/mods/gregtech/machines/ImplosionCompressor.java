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
 * Provides access to the Implosion Compressor recipes.
 * 
 * @author Stan Hebben
 */
@ZenClass("mods.gregtech.ImplosionCompressor")
@ModOnly("gregtech")
public class ImplosionCompressor {
	/**
	 * Adds an implosion compressor recipe with a single output.
	 * 
	 * @param output recipe output
	 * @param input primary input
	 * @param tnt amount of TNT needed
	 */
	@ZenMethod
	public static void addRecipe(IItemStack output, IItemStack input, int tnt) {
		MineTweakerAPI.apply(new AddRecipeAction(output, null, input, tnt));
	}
	
	/**
	 * Adds an implosion compressor recipe with one or two outputs.
	 * 
	 * @param output array with 1-2 outputs
	 * @param input primary input
	 * @param tnt amount of TNT needed
	 */
	@ZenMethod
	public static void addRecipe(IItemStack[] output, IItemStack input, int tnt) {
		if (output.length == 0) {
			MineTweakerAPI.logError("Implosion compressor recipe requires at least 1 output");
		} else {
			MineTweakerAPI.apply(new AddRecipeAction(output[0], output.length > 1 ? output[1] : null, input, tnt));
		}
	}
	
	// ######################
	// ### Action classes ###
	// ######################
	
	private static class AddRecipeAction extends OneWayAction {
		private final IItemStack output1;
		private final IItemStack output2;
		private final IItemStack input1;
		private final int tnt;
		
		public AddRecipeAction(IItemStack output1, IItemStack output2, IItemStack input1, int tnt) {
			this.output1 = output1;
			this.output2 = output2;
			this.input1 = input1;
			this.tnt = tnt;
		}

		@Override
		public void apply() {
			GregTech_API.sRecipeAdder.addImplosionRecipe(
					MineTweakerMC.getItemStack(input1),
					tnt,
					MineTweakerMC.getItemStack(output1),
					MineTweakerMC.getItemStack(output2));
		}

		@Override
		public String describe() {
			return "Adding Implosion compressor recipe for " + output1;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public int hashCode() {
			int hash = 7;
			hash = 97 * hash + (this.output1 != null ? this.output1.hashCode() : 0);
			hash = 97 * hash + (this.output2 != null ? this.output2.hashCode() : 0);
			hash = 97 * hash + (this.input1 != null ? this.input1.hashCode() : 0);
			hash = 97 * hash + this.tnt;
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
			if (this.tnt != other.tnt) {
				return false;
			}
			return true;
		}
	}
}
