/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
 *
 * @author Stan
 */
@ZenClass("mods.gregtech.Lathe")
@ModOnly("gregtech_addon")
public class Lathe {
	@ZenMethod
	public static void addRecipe(IItemStack output, IItemStack input, int duration, int euPerTick) {
		MineTweakerAPI.apply(new AddRecipeAction(output, null, input, duration, euPerTick));
	}
	
	@ZenMethod
	public static void addRecipe(IItemStack[] outputs, IItemStack input, int duration, int euPerTick) {
		if (outputs.length == 0) {
			MineTweakerAPI.logError("Lathe recipe requires at least 1 input");
		} else {
			MineTweakerAPI.apply(new AddRecipeAction(
					outputs[0],
					outputs.length > 1 ? outputs[1] : null,
					input,
					duration,
					euPerTick));
		}
	}
	
	private static class AddRecipeAction extends OneWayAction {
		private final IItemStack output1;
		private final IItemStack output2;
		private final IItemStack input;
		private final int duration;
		private final int euPerTick;
		
		public AddRecipeAction(IItemStack output1, IItemStack output2, IItemStack input, int duration, int euPerTick) {
			this.output1 = output1;
			this.output2 = output2;
			this.input = input;
			this.duration = duration;
			this.euPerTick = euPerTick;
		}

		@Override
		public void apply() {
			GregTech_API.sRecipeAdder.addLatheRecipe(
					MineTweakerMC.getItemStack(input),
					MineTweakerMC.getItemStack(output1),
					MineTweakerMC.getItemStack(output2),
					duration,
					euPerTick);
		}

		@Override
		public String describe() {
			return "Adding lathe recipe for " + output1;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public int hashCode() {
			int hash = 5;
			hash = 53 * hash + (this.output1 != null ? this.output1.hashCode() : 0);
			hash = 53 * hash + (this.output2 != null ? this.output2.hashCode() : 0);
			hash = 53 * hash + (this.input != null ? this.input.hashCode() : 0);
			hash = 53 * hash + this.duration;
			hash = 53 * hash + this.euPerTick;
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
			if (this.input != other.input && (this.input == null || !this.input.equals(other.input))) {
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
