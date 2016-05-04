/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1710.liquid;

import java.util.ArrayList;
import java.util.List;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidDefinition;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
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
		MineTweakerAPI.apply(new ActionSetLuminosity(value));
	}

	@Override
	public int getDensity() {
		return fluid.getDensity();
	}

	@Override
	public void setDensity(int density) {
		MineTweakerAPI.apply(new ActionSetDensity(density));
	}

	@Override
	public int getTemperature() {
		return fluid.getTemperature();
	}

	@Override
	public void setTemperature(int temperature) {
		MineTweakerAPI.apply(new ActionSetTemperature(temperature));
	}

	@Override
	public int getViscosity() {
		return fluid.getViscosity();
	}

	@Override
	public void setViscosity(int viscosity) {
		MineTweakerAPI.apply(new ActionSetViscosity(viscosity));
	}

	@Override
	public boolean isGaseous() {
		return fluid.isGaseous();
	}

	@Override
	public void setGaseous(boolean gaseous) {
		MineTweakerAPI.apply(new ActionSetGaseous(gaseous));
	}

	@Override
	public List<IItemStack> getContainers() {
		List<IItemStack> result = new ArrayList<IItemStack>();
		for (FluidContainerRegistry.FluidContainerData data : FluidContainerRegistry.getRegisteredFluidContainerData()) {
			if (data.fluid.getFluid() == fluid) {
				result.add(MineTweakerMC.getIItemStack(data.filledContainer));
			}
		}
		return result;
	}

	@Override
	public void addContainer(IItemStack filled, IItemStack empty, int amount) {
		MineTweakerAPI.apply(new AddContainerAction(filled, empty, amount));
	}

	@Override
	public void removeContainer(IItemStack filled) {
		// MineTweakerAPI.apply(new RemoveContainerAction(filled));
		MineTweakerAPI.logError("Cannot remove container items in MineCraft 1.7.X");
	}

	// #######################
	// ### Private methods ###
	// #######################

	/*
	 * private void removeContainerInner(IItemStack filled) { ItemStack
	 * filledItem = MineTweakerMC.getItemStack(filled);
	 * FluidContainerRegistry.FluidContainerData data =
	 * filledContainerMap.get(Arrays.asList(filledItem.itemID,
	 * filledItem.getItemDamage())); if (data != null) {
	 * filledContainerMap.remove(Arrays.asList(filledItem.itemID,
	 * filledItem.getItemDamage()));
	 * containerFluidMap.remove(Arrays.asList(data.emptyContainer.itemID,
	 * data.emptyContainer.getItemDamage()));
	 * 
	 * // rebuild empty containers set emptyContainers.clear(); for
	 * (FluidContainerRegistry.FluidContainerData fdata :
	 * filledContainerMap.values()) {
	 * emptyContainers.add(Arrays.asList(fdata.emptyContainer.itemID,
	 * fdata.emptyContainer.getItemDamage())); } }
	 * MineTweakerAPI.logError("Removal of containers is not possible in 1.7.X"
	 * ); }
	 */

	/*
	 * private FluidContainerRegistry.FluidContainerData getData(IItemStack
	 * filled) { ItemStack filledStack = MineTweakerMC.getItemStack(filled);
	 * return filledContainerMap.get(Arrays.asList(filledStack.itemID,
	 * filledStack.getItemDamage())); }
	 */

	// ######################
	// ### Action classes ###
	// ######################

	private class AddContainerAction implements IUndoableAction {
		private final IItemStack filled;
		private final IItemStack empty;
		private final int amount;

		public AddContainerAction(IItemStack filled, IItemStack empty, int amount) {
			this.filled = filled;
			this.empty = empty;
			this.amount = amount;
		}

		@Override
		public void apply() {
			FluidContainerRegistry.registerFluidContainer(new FluidStack(fluid, amount), MineTweakerMC.getItemStack(filled), MineTweakerMC.getItemStack(empty));
		}

		@Override
		public boolean canUndo() {
			return false;
			// return true;
		}

		@Override
		public void undo() {
			// removeContainerInner(filled);
		}

		@Override
		public String describe() {
			return "Adding " + filled.getDisplayName() + " as liquid container for " + fluid.getLocalizedName();
		}

		@Override
		public String describeUndo() {
			return "Removing liquid container " + filled;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}

	/*
	 * private class RemoveContainerAction implements IUndoableAction { private
	 * final IItemStack filled; private final IItemStack empty; private final
	 * FluidStack amount;
	 * 
	 * public RemoveContainerAction(IItemStack filled) { this.filled = filled;
	 * 
	 * FluidContainerRegistry.FluidContainerData data = getData(filled); empty =
	 * MineTweakerMC.getIItemStack(data.emptyContainer); amount = data.fluid; }
	 * 
	 * @Override public void apply() { removeContainerInner(filled); }
	 * 
	 * @Override public boolean canUndo() { return true; }
	 * 
	 * @Override public void undo() {
	 * FluidContainerRegistry.registerFluidContainer(amount,
	 * MineTweakerMC.getItemStack(filled), MineTweakerMC.getItemStack(empty)); }
	 * 
	 * @Override public String describe() { return "Removing liquid container "
	 * + filled; }
	 * 
	 * @Override public String describeUndo() { return
	 * "Restoring liquid container " + filled; }
	 * 
	 * @Override public Object getOverrideKey() { return null; } }
	 */

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

		@Override
		public Object getOverrideKey() {
			return null;
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

		@Override
		public Object getOverrideKey() {
			return null;
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

		@Override
		public Object getOverrideKey() {
			return null;
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

		@Override
		public Object getOverrideKey() {
			return null;
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

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
}
