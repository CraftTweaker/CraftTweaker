package minetweaker.mods.gregtech.machines;

import gregtechmod.api.GregTech_API;
import gregtechmod.api.util.GT_ModHandler;
import minetweaker.MineTweakerAPI;
import minetweaker.OneWayAction;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * Provides access to the Sawmill recipes.
 * 
 * @author Stan Hebben
 */
@ZenClass("mods.gregtech.Sawmill")
@ModOnly("gregtech_addon")
public class Sawmill {
	/**
	 * Adds a new sawmill recipe.
	 * 
	 * @param output recipe output
	 * @param input1 primary input
	 * @param input2 secondary input (optional, if set to null will use a water cell)
	 */
	@ZenMethod
	public static void addRecipe(IItemStack output, IItemStack input1, @Optional IItemStack input2) {
		if (input2 == null) input2 = MineTweakerMC.getIItemStack(GT_ModHandler.getWaterCell(1));
		MineTweakerAPI.apply(new AddRecipeAction(output, null, null, input1, input2));
	}
	
	/**
	 * Adds a new sawmill recipe with 1-3 outputs.
	 * 
	 * @param outputs array with 1-3 outputs
	 * @param input1 primary input
	 * @param input2 secondary input (optional, if set to null will use a water cell)
	 */
	@ZenMethod
	public static void addRecipe(IItemStack[] outputs, IItemStack input1, @Optional IItemStack input2) {
		if (input2 == null) input2 = MineTweakerMC.getIItemStack(GT_ModHandler.getWaterCell(1));
		MineTweakerAPI.apply(new AddRecipeAction(
				outputs[0],
				outputs.length > 1 ? outputs[1] : null,
				outputs.length > 2 ? outputs[2] : null,
				input1,
				input2));
	}
	
	// ######################
	// ### Action classes ###
	// ######################
	
	private static class AddRecipeAction extends OneWayAction {
		private final IItemStack output1;
		private final IItemStack output2;
		private final IItemStack output3;
		private final IItemStack input1;
		private final IItemStack input2;
		
		public AddRecipeAction(IItemStack output1, IItemStack output2, IItemStack output3, IItemStack input1, IItemStack input2) {
			this.output1 = output1;
			this.output2 = output2;
			this.output3 = output3;
			this.input1 = input1;
			this.input2 = input2;
		}

		@Override
		public void apply() {
			GregTech_API.sRecipeAdder.addSawmillRecipe(
					MineTweakerMC.getItemStack(input1), 
					MineTweakerMC.getItemStack(input2),
					MineTweakerMC.getItemStack(output1),
					MineTweakerMC.getItemStack(output2),
					MineTweakerMC.getItemStack(output3));
		}

		@Override
		public String describe() {
			return "Adding Sawmill recipe for " + output1;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public int hashCode() {
			int hash = 7;
			hash = 23 * hash + (this.output1 != null ? this.output1.hashCode() : 0);
			hash = 23 * hash + (this.output2 != null ? this.output2.hashCode() : 0);
			hash = 23 * hash + (this.output3 != null ? this.output3.hashCode() : 0);
			hash = 23 * hash + (this.input1 != null ? this.input1.hashCode() : 0);
			hash = 23 * hash + (this.input2 != null ? this.input2.hashCode() : 0);
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
			if (this.output3 != other.output3 && (this.output3 == null || !this.output3.equals(other.output3))) {
				return false;
			}
			if (this.input1 != other.input1 && (this.input1 == null || !this.input1.equals(other.input1))) {
				return false;
			}
			if (this.input2 != other.input2 && (this.input2 == null || !this.input2.equals(other.input2))) {
				return false;
			}
			return true;
		}
	}
}
