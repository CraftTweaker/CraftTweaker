/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mods.buildcraft;

import buildcraft.api.core.StackWrapper;
import buildcraft.api.fuels.IronEngineCoolant;
import buildcraft.api.fuels.IronEngineFuel;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author Stan
 */
@ZenClass("mods.buildcraft.Fuels")
@ModOnly("BuildCraft|Core")
public class Fuels {
	private static final Constructor CONSTRUCT_FUEL;
	
	static {
		Constructor newFuel = null;
		try {
			newFuel = IronEngineFuel.Fuel.class.getDeclaredConstructor(Fluid.class, float.class, int.class);
			newFuel.setAccessible(true);
		} catch (NoSuchMethodException ex) {
			MineTweakerAPI.logError("Could not load combustion engine fuel constructor", ex);
		} catch (SecurityException ex) {
			MineTweakerAPI.logError("Could not load combustion engine fuel constructor", ex);
		}
		
		CONSTRUCT_FUEL = newFuel;
	}
	
	@ZenMethod
	public static void addCombustionEngineFuel(ILiquidStack fuel, double powerPerCycle, int totalBurningTime) {
		MineTweakerAPI.apply(new AddFuelAction(fuel, powerPerCycle, totalBurningTime));
	}
	
	@ZenMethod
	public static void removeCombustionEngineFuel(ILiquidStack fuel) {
		Fluid fluid = MineTweakerMC.getLiquidStack(fuel).getFluid();
		if (IronEngineFuel.fuels.containsKey(fluid.getName())) {
			MineTweakerAPI.apply(new RemoveFuelAction(fluid.getName(), IronEngineFuel.fuels.get(fluid.getName())));
		} else {
			MineTweakerAPI.logWarning("No such iron engine fuel: " + fluid.getName());
		}
	}
	
	@ZenMethod
	public static void addCombustionEngineCoolant(ILiquidStack coolant, float coolingPerMB) {
		MineTweakerAPI.apply(new AddLiquidCoolantAction(coolant.getName(), new MTCoolant(coolingPerMB)));
	}
	
	@ZenMethod
	public static void addCoolantItem(IItemStack coolantItem, ILiquidStack coolantLiquid) {
		MineTweakerAPI.apply(new AddCoolantItemAction(new StackWrapper(MineTweakerMC.getItemStack(coolantItem)), MineTweakerMC.getLiquidStack(coolantLiquid)));
	}
	
	@ZenMethod
	public static void removeCombustionEngineCoolant(ILiquidStack coolant) {
		FluidStack fluid = MineTweakerMC.getLiquidStack(coolant);
		
		if (IronEngineCoolant.isCoolant(fluid.getFluid())) {
			MineTweakerAPI.apply(new RemoveLiquidCoolantAction(fluid.getFluid().getName(), IronEngineCoolant.getCoolant(fluid)));
		} else {
			MineTweakerAPI.logWarning("No such iron engine coolant: " + fluid.getFluid().getName());
		}
	}
	
	@ZenMethod
	public static void removeCoolantItem(IIngredient item) {
		int numRemoved = 0;
		for (IItemStack itemStack : item.getItems()) {
			StackWrapper key = new StackWrapper(MineTweakerMC.getItemStack(itemStack));
			if (IronEngineCoolant.solidCoolants.containsKey(key)) {
				MineTweakerAPI.apply(new RemoveCoolantItemAction(key, IronEngineCoolant.solidCoolants.get(key)));
				numRemoved++;
			}
		}
		
		if (numRemoved == 0) {
			MineTweakerAPI.logWarning("No such iron engine coolant: " + item);
		}
	}
	
	// ######################
	// ### Action classes ###
	// ######################
	
	private static class AddFuelAction implements IUndoableAction {
		private final String fuelName;
		private final IronEngineFuel.Fuel fuel;
		
		public AddFuelAction(ILiquidStack fuel, double powerPerCycle, int totalBurningTime) {
			fuelName = fuel.getName();
			IronEngineFuel.Fuel ieFuel = null;
			try {
				if (CONSTRUCT_FUEL == null)
					System.out.println("No constructor for fuel");
				
				ieFuel = (IronEngineFuel.Fuel) CONSTRUCT_FUEL.newInstance(MineTweakerMC.getLiquidStack(fuel).getFluid(), (float) powerPerCycle, totalBurningTime);
			} catch (InstantiationException ex) {
				Logger.getLogger(Fuels.class.getName()).log(Level.SEVERE, null, ex);
			} catch (IllegalAccessException ex) {
				Logger.getLogger(Fuels.class.getName()).log(Level.SEVERE, null, ex);
			} catch (IllegalArgumentException ex) {
				Logger.getLogger(Fuels.class.getName()).log(Level.SEVERE, null, ex);
			} catch (InvocationTargetException ex) {
				Logger.getLogger(Fuels.class.getName()).log(Level.SEVERE, null, ex);
			}
			
			this.fuel = ieFuel;
		}

		@Override
		public void apply() {
			IronEngineFuel.fuels.put(fuelName, fuel);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			IronEngineFuel.fuels.remove(fuelName);
		}

		@Override
		public String describe() {
			return "Adding iron engine fuel " + fuelName;
		}

		@Override
		public String describeUndo() {
			return "Removing iron engine fuel " + fuelName;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	private static class RemoveFuelAction implements IUndoableAction {
		private final String fuelName;
		private final IronEngineFuel.Fuel fuel;
		
		public RemoveFuelAction(String fuelName, IronEngineFuel.Fuel fuel) {
			this.fuelName = fuelName;
			this.fuel = fuel;
		}

		@Override
		public void apply() {
			IronEngineFuel.fuels.remove(fuelName);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			IronEngineFuel.fuels.put(fuelName, fuel);
		}

		@Override
		public String describe() {
			return "Removing iron engine fuel " + fuelName;
		}

		@Override
		public String describeUndo() {
			return "Restoring iron engine fuel " + fuelName;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	private static class AddLiquidCoolantAction implements IUndoableAction {
		private final String liquid;
		private final IronEngineCoolant.Coolant coolant;
		
		public AddLiquidCoolantAction(String liquid, IronEngineCoolant.Coolant coolant) {
			this.liquid = liquid;
			this.coolant = coolant;
		}

		@Override
		public void apply() {
			IronEngineCoolant.liquidCoolants.put(liquid, coolant);
		}

		@Override
		public boolean canUndo() {
			return false;
		}

		@Override
		public void undo() {
			IronEngineCoolant.liquidCoolants.remove(liquid);
		}

		@Override
		public String describe() {
			return "Adding iron engine coolant " + liquid;
		}

		@Override
		public String describeUndo() {
			return "Removing iron engine coolant " + liquid;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	private static class AddCoolantItemAction implements IUndoableAction {
		private final StackWrapper item;
		private final FluidStack liquid;
		
		public AddCoolantItemAction(StackWrapper item, FluidStack liquid) {
			this.item = item;
			this.liquid = liquid;
		}

		@Override
		public void apply() {
			IronEngineCoolant.solidCoolants.put(item, liquid);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			IronEngineCoolant.solidCoolants.remove(item);
		}

		@Override
		public String describe() {
			return "Adding iron engine coolant item " + MineTweakerMC.getIItemStack(item.stack);
		}

		@Override
		public String describeUndo() {
			return "Removing iron engine coolant item " + MineTweakerMC.getIItemStack(item.stack);
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	private static class RemoveLiquidCoolantAction implements IUndoableAction {
		private final String liquid;
		private final IronEngineCoolant.Coolant coolant;
		
		public RemoveLiquidCoolantAction(String liquid, IronEngineCoolant.Coolant coolant) {
			this.liquid = liquid;
			this.coolant = coolant;
		}

		@Override
		public void apply() {
			IronEngineCoolant.liquidCoolants.remove(liquid);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			IronEngineCoolant.liquidCoolants.put(liquid, coolant);
		}

		@Override
		public String describe() {
			return "Removing iron engine coolant " + liquid;
		}

		@Override
		public String describeUndo() {
			return "Restoring iron engine coolant " + liquid;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	private static class RemoveCoolantItemAction implements IUndoableAction {
		private final StackWrapper item;
		private final FluidStack liquid;
		
		public RemoveCoolantItemAction(StackWrapper item, FluidStack liquid) {
			this.item = item;
			this.liquid = liquid;
		}

		@Override
		public void apply() {
			IronEngineCoolant.solidCoolants.remove(item);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			IronEngineCoolant.solidCoolants.put(item, liquid);
		}

		@Override
		public String describe() {
			return "Removing iron engine coolant item " + item.stack.getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Restoring iron engine coolant item " + item.stack.getDisplayName();
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	private static class MTCoolant implements IronEngineCoolant.Coolant {
		private final float coolingPerMB;
		
		public MTCoolant(float coolingPerMB) {
			this.coolingPerMB = coolingPerMB;
		}
		
		@Override
		public float getDegreesCoolingPerMB(float f) {
			return coolingPerMB;
		}
	}
}
