/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mods.ic2.machines;

import ic2.api.recipe.Recipes;
import minetweaker.MineTweakerAPI;
import minetweaker.OneWayAction;
import minetweaker.annotations.ModOnly;
import minetweaker.api.liquid.ILiquidStack;
import net.minecraftforge.fluids.FluidRegistry;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * Add a fluid to the semi-fluid generator
 * @author Stan
 */
@ZenClass("mods.ic2.SemiFluidGenerator")
@ModOnly("IC2")
public class SemiFluidGenerator {
	@ZenMethod
	public static void addFluid(ILiquidStack liquidPerTick, int energyPerTick) {
		MineTweakerAPI.apply(new AddFluidAction(liquidPerTick.getName(), liquidPerTick.getAmount(), energyPerTick));
	}
	
	@ZenMethod
	public static boolean accepts(ILiquidStack liquid) {
		return Recipes.semiFluidGenerator.acceptsFluid(FluidRegistry.getFluid(liquid.getName()));
	}
	
	private static class AddFluidAction extends OneWayAction {
		private final String name;
		private final int liquidPerTick;
		private final int energyPerTick;
		
		public AddFluidAction(String name, int liquidPerTick, int energyPerTick) {
			this.name = name;
			this.liquidPerTick = liquidPerTick;
			this.energyPerTick = energyPerTick;
		}
		
		@Override
		public void apply() {
			Recipes.semiFluidGenerator.addFluid(name, liquidPerTick, energyPerTick);
		}

		@Override
		public String describe() {
			return "Adding liquid " + name + " as semifluid fuel";
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public int hashCode() {
			int hash = 7;
			hash = 47 * hash + (this.name != null ? this.name.hashCode() : 0);
			hash = 47 * hash + this.liquidPerTick;
			hash = 47 * hash + this.energyPerTick;
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
			final AddFluidAction other = (AddFluidAction) obj;
			if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
				return false;
			}
			if (this.liquidPerTick != other.liquidPerTick) {
				return false;
			}
			if (this.energyPerTick != other.energyPerTick) {
				return false;
			}
			return true;
		}
	}
}
