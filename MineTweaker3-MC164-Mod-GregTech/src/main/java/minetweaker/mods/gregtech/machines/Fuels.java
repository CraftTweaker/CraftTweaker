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
@ZenClass("mods.gregtech.Fuels")
@ModOnly("gregtech_addon")
public class Fuels {
	@ZenMethod
	public static void addDieselFuel(IItemStack output, IItemStack input, int euPerMillibucket) {
		MineTweakerAPI.apply(new AddRecipeAction(output, input, euPerMillibucket, 0));
	}
	
	@ZenMethod
	public static void addGasTurbineFuel(IItemStack output, IItemStack input, int euPerMillibucket) {
		MineTweakerAPI.apply(new AddRecipeAction(output, input, euPerMillibucket, 1));
	}
	
	@ZenMethod
	public static void addThermalGeneratorFuel(IItemStack output, IItemStack input, int euPerMillibucket) {
		MineTweakerAPI.apply(new AddRecipeAction(output, input, euPerMillibucket, 2));
	}
	
	@ZenMethod
	public static void addDenseFluidFuel(IItemStack output, IItemStack input, int euPerMillibucket) {
		MineTweakerAPI.apply(new AddRecipeAction(output, input, euPerMillibucket, 3));
	}
	
	@ZenMethod
	public static void addPlasmaGeneratorFuel(IItemStack output, IItemStack input, int euPerMillibucket) {
		MineTweakerAPI.apply(new AddRecipeAction(output, input, euPerMillibucket, 4));
	}
	
	@ZenMethod
	public static void addMagicGeneratorFuel(IItemStack output, IItemStack input, int euPerMillibucket) {
		MineTweakerAPI.apply(new AddRecipeAction(output, input, euPerMillibucket, 5));
	}
	
	private static class AddRecipeAction extends OneWayAction {
		private static final String[] GENERATORS = {
			"diesel",
			"gas turbine",
			"thermal",
			"dense fluid",
			"plasma",
			"magic"
		};
		
		private final IItemStack output;
		private final IItemStack input;
		private final int euPerMillibucket;
		private final int type;
		
		public AddRecipeAction(IItemStack output, IItemStack input, int euPerMillibucket, int type) {
			this.output = output;
			this.input = input;
			this.euPerMillibucket = euPerMillibucket;
			this.type = type;
		}

		@Override
		public void apply() {
			GregTech_API.sRecipeAdder.addFuel(
					MineTweakerMC.getItemStack(input),
					MineTweakerMC.getItemStack(output),
					euPerMillibucket,
					type);
		}

		@Override
		public String describe() {
			return "Adding " + GENERATORS[type] + " fuel " + input; 
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public int hashCode() {
			int hash = 5;
			hash = 59 * hash + (this.output != null ? this.output.hashCode() : 0);
			hash = 59 * hash + (this.input != null ? this.input.hashCode() : 0);
			hash = 59 * hash + this.euPerMillibucket;
			hash = 59 * hash + this.type;
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
			if (this.euPerMillibucket != other.euPerMillibucket) {
				return false;
			}
			if (this.type != other.type) {
				return false;
			}
			return true;
		}
	}
}
