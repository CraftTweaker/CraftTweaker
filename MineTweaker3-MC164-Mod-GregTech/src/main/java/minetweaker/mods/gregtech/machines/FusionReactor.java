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
@ZenClass("mods.gregtech.FusionReactor")
@ModOnly("gregtech_addon")
public class FusionReactor {
	@ZenMethod
	public static void addRecipe(IItemStack output, IItemStack input1, IItemStack input2, int durationTicks, int energyPerTick, int startEnergy) {
		MineTweakerAPI.apply(new AddRecipeAction(output, input1, input2, durationTicks, energyPerTick, startEnergy));
	}
	
	private static class AddRecipeAction extends OneWayAction {
		private final IItemStack output;
		private final IItemStack input1;
		private final IItemStack input2;
		private final int durationTicks;
		private final int energyPerTick;
		private final int startEnergy;
		
		public AddRecipeAction(
				IItemStack output,
				IItemStack input1,
				IItemStack input2,
				int durationTicks,
				int energyPerTick,
				int startEnergy) {
			this.output = output;
			this.input1 = input1;
			this.input2 = input2;
			this.durationTicks = durationTicks;
			this.energyPerTick = energyPerTick;
			this.startEnergy = startEnergy;
		}

		@Override
		public void apply() {
			GregTech_API.sRecipeAdder.addFusionReactorRecipe(
					MineTweakerMC.getItemStack(input1),
					MineTweakerMC.getItemStack(input2),
					MineTweakerMC.getItemStack(output),
					durationTicks,
					energyPerTick,
					startEnergy);
		}

		@Override
		public String describe() {
			return "Adding fusion reactor recipe for " + output;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public int hashCode() {
			int hash = 5;
			hash = 97 * hash + (this.output != null ? this.output.hashCode() : 0);
			hash = 97 * hash + (this.input1 != null ? this.input1.hashCode() : 0);
			hash = 97 * hash + (this.input2 != null ? this.input2.hashCode() : 0);
			hash = 97 * hash + this.durationTicks;
			hash = 97 * hash + this.energyPerTick;
			hash = 97 * hash + this.startEnergy;
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
			if (this.durationTicks != other.durationTicks) {
				return false;
			}
			if (this.energyPerTick != other.energyPerTick) {
				return false;
			}
			if (this.startEnergy != other.startEnergy) {
				return false;
			}
			return true;
		}
	}
}
