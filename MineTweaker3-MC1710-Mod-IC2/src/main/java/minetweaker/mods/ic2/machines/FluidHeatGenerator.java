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
 * Add a fluid to the fluid heat generator
 * @author Stan
 */
@ZenClass("mods.ic2.FluidHeatGenerator")
@ModOnly("IC2")
public class FluidHeatGenerator {
	@ZenMethod
	public static void addFluid(ILiquidStack liquidPerTick, int heatPerTick) {
		MineTweakerAPI.apply(new AddFluidAction(liquidPerTick.getName(), liquidPerTick.getAmount(), heatPerTick));
	}
	
	@ZenMethod
	public static boolean accepts(ILiquidStack liquid) {
		return Recipes.FluidHeatGenerator.acceptsFluid(FluidRegistry.getFluid(liquid.getName()));
	}
	
	private static class AddFluidAction extends OneWayAction {
		private final String name;
		private final int liquidPerTick;
		private final int heatPerTick;
		
		public AddFluidAction(String name, int liquidPerTick, int heatPerTick) {
			this.name = name;
			this.liquidPerTick = liquidPerTick;
			this.heatPerTick = heatPerTick;
		}
		
		@Override
		public void apply() {
			Recipes.FluidHeatGenerator.addFluid(name, liquidPerTick, heatPerTick);
		}

		@Override
		public String describe() {
			return "Adding liquid " + name + " as fluid heat generator fuel";
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
			hash = 47 * hash + this.heatPerTick;
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
			if (this.heatPerTick != other.heatPerTick) {
				return false;
			}
			return true;
		}
	}
}
