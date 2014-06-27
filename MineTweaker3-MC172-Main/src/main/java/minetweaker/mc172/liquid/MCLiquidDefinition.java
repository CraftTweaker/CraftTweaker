/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc172.liquid;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.liquid.ILiquidDefinition;
import minetweaker.api.liquid.ILiquidStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

/**
 *
 * @author Stan
 */
public class MCLiquidDefinition implements ILiquidDefinition {
	private final Fluid fluid;
	
	public MCLiquidDefinition(Fluid fluid) {
		this.fluid = fluid;
	}
	
	@Override
	public String getName() {
		return fluid.getName();
	}
	
	@Override
	public String getDisplayName() {
		return fluid.getLocalizedName();
	}

	@Override
	public ILiquidStack asStack(int millibuckets) {
		return new MCLiquidStack(new FluidStack(fluid, millibuckets));
	}

	@Override
	public int getLuminosity() {
		return fluid.getLuminosity();
	}

	@Override
	public void setLuminosity(int value) {
		MineTweakerAPI.tweaker.apply(new ActionSetLuminosity(value));
	}

	@Override
	public int getDensity() {
		return fluid.getDensity();
	}

	@Override
	public void setDensity(int density) {
		MineTweakerAPI.tweaker.apply(new ActionSetDensity(density));
	}

	@Override
	public int getTemperature() {
		return fluid.getTemperature();
	}

	@Override
	public void setTemperature(int temperature) {
		MineTweakerAPI.tweaker.apply(new ActionSetTemperature(temperature));
	}

	@Override
	public int getViscosity() {
		return fluid.getViscosity();
	}

	@Override
	public void setViscosity(int viscosity) {
		MineTweakerAPI.tweaker.apply(new ActionSetViscosity(viscosity));
	}

	@Override
	public boolean isGaseous() {
		return fluid.isGaseous();
	}

	@Override
	public void setGaseous(boolean gaseous) {
		MineTweakerAPI.tweaker.apply(new ActionSetGaseous(gaseous));
	}
	
	// ######################
	// ### Action classes ###
	// ######################
	
	private class ActionSetLuminosity implements IUndoableAction {
		private final int oldValue;
		private final int newValue;
		
		public ActionSetLuminosity(int newValue) {
			oldValue = getLuminosity();
			this.newValue = newValue;
		}
		
		@Override
		public void apply() {
			fluid.setLuminosity(newValue);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			fluid.setLuminosity(oldValue);
		}

		@Override
		public String describe() {
			return "Setting " + fluid.getName() + " luminosity to " + newValue;
		}

		@Override
		public String describeUndo() {
			return "Restoring " + fluid.getName() + " luminosity to " + oldValue;
		}
	}
	
	private class ActionSetDensity implements IUndoableAction {
		private final int oldValue;
		private final int newValue;
		
		public ActionSetDensity(int newValue) {
			oldValue = getDensity();
			this.newValue = newValue;
		}
		
		@Override
		public void apply() {
			fluid.setDensity(newValue);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			fluid.setDensity(oldValue);
		}

		@Override
		public String describe() {
			return "Setting " + fluid.getName() + " density to " + newValue;
		}

		@Override
		public String describeUndo() {
			return "Restoring " + fluid.getName() + " density to " + oldValue;
		}
	}
	
	private class ActionSetTemperature implements IUndoableAction {
		private final int oldValue;
		private final int newValue;
		
		public ActionSetTemperature(int newValue) {
			oldValue = getTemperature();
			this.newValue = newValue;
		}
		
		@Override
		public void apply() {
			fluid.setTemperature(newValue);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			fluid.setTemperature(oldValue);
		}

		@Override
		public String describe() {
			return "Setting " + fluid.getName() + " temperature to " + newValue;
		}

		@Override
		public String describeUndo() {
			return "Restoring " + fluid.getName() + " temperature to " + oldValue;
		}
	}
	
	private class ActionSetViscosity implements IUndoableAction {
		private final int oldValue;
		private final int newValue;
		
		public ActionSetViscosity(int newValue) {
			oldValue = getViscosity();
			this.newValue = newValue;
		}
		
		@Override
		public void apply() {
			fluid.setViscosity(newValue);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			fluid.setViscosity(oldValue);
		}

		@Override
		public String describe() {
			return "Setting " + fluid.getName() + " viscosity to " + newValue;
		}

		@Override
		public String describeUndo() {
			return "Restoring " + fluid.getName() + " viscosity to " + oldValue;
		}
	}
	
	private class ActionSetGaseous implements IUndoableAction {
		private final boolean oldValue;
		private final boolean newValue;
		
		public ActionSetGaseous(boolean newValue) {
			oldValue = isGaseous();
			this.newValue = newValue;
		}
		
		@Override
		public void apply() {
			fluid.setGaseous(newValue);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			fluid.setGaseous(oldValue);
		}

		@Override
		public String describe() {
			return "Setting " + fluid.getName() + " gaseous to " + newValue;
		}

		@Override
		public String describeUndo() {
			return "Restoring " + fluid.getName() + " gaseous to " + oldValue;
		}
	}
}
