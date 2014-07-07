/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mods.gregtech.machines;

import gregtechmod.api.GregTech_API;
import gregtechmod.api.util.GT_ModHandler;
import java.util.Arrays;
import minetweaker.MineTweakerAPI;
import minetweaker.OneWayAction;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author Stan
 */
@ZenClass("mods.gregtech.Grinder")
@ModOnly("gregtech_addon")
public class Grinder {
	@ZenMethod
	public static void addRecipe(IItemStack output, IItemStack input1, @Optional IItemStack input2) {
		if (input2 == null) input2 = MineTweakerMC.getIItemStack(GT_ModHandler.getWaterCell(1));
		MineTweakerAPI.apply(new AddRecipeAction(new IItemStack[] { output }, input1, input2));
	}
	
	@ZenMethod
	public static void addRecipe(IItemStack[] outputs, IItemStack input1, @Optional IItemStack input2) {
		if (input2 == null) input2 = MineTweakerMC.getIItemStack(GT_ModHandler.getWaterCell(1));
		MineTweakerAPI.apply(new AddRecipeAction(outputs, input1, input2));
	}
	
	private static class AddRecipeAction extends OneWayAction {
		private final IItemStack[] outputs;
		private final IItemStack input1;
		private final IItemStack input2;
		
		private AddRecipeAction(IItemStack[] outputs, IItemStack input1, IItemStack input2) {
			this.outputs = outputs;
			this.input1 = input1;
			this.input2 = input2;
		}

		@Override
		public void apply() {
			GregTech_API.sRecipeAdder.addGrinderRecipe(
					MineTweakerMC.getItemStack(input1),
					MineTweakerMC.getItemStack(input2),
					MineTweakerMC.getItemStack(outputs[0]),
					outputs.length > 1 ? MineTweakerMC.getItemStack(outputs[1]) : null,
					outputs.length > 2 ? MineTweakerMC.getItemStack(outputs[2]) : null,
					outputs.length > 3 ? MineTweakerMC.getItemStack(outputs[3]) : null);
		}

		@Override
		public String describe() {
			return "Adding grinder recipe for " + outputs[0];
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public int hashCode() {
			int hash = 5;
			hash = 89 * hash + Arrays.deepHashCode(this.outputs);
			hash = 89 * hash + (this.input1 != null ? this.input1.hashCode() : 0);
			hash = 89 * hash + (this.input2 != null ? this.input2.hashCode() : 0);
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
