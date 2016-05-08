/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mods.buildcraft;

import buildcraft.api.recipes.BuildcraftRecipes;
import buildcraft.core.recipes.RefineryRecipeManager;
import buildcraft.core.recipes.RefineryRecipeManager.RefineryRecipe;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author Stan
 */
@ZenClass("mods.buildcraft.Refinery")
@ModOnly(value="BuildCraft|Core", version="6.0")
public class Refinery {
	private static final Constructor CONSTRUCT_REFINERYRECIPE;
	private static final Field REFINERYRECIPEMANAGER_RECIPES;
	
	static {
		Constructor constructor = null;
		try {
			constructor = RefineryRecipe.class.getDeclaredConstructor(FluidStack.class, FluidStack.class, FluidStack.class, int.class, int.class);
			constructor.setAccessible(true);
		} catch (NoSuchMethodException ex) {
			Logger.getLogger(Refinery.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SecurityException ex) {
			Logger.getLogger(Refinery.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		Field recipes = null;
		try {
			recipes = RefineryRecipeManager.class.getDeclaredField("recipes");
			recipes.setAccessible(true);
		} catch (NoSuchFieldException ex) {
			Logger.getLogger(Refinery.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SecurityException ex) {
			Logger.getLogger(Refinery.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		CONSTRUCT_REFINERYRECIPE = constructor;
		REFINERYRECIPEMANAGER_RECIPES = recipes;
	}
	
	
	@ZenMethod
	public static void addRecipe(ILiquidStack output, int energyPerMB, int ticksPerMB, ILiquidStack input1, @Optional ILiquidStack input2) {
		MineTweakerAPI.apply(new AddRecipeAction(output, energyPerMB, ticksPerMB, input1, input2));
	}
	
	@ZenMethod
	public static void remove(ILiquidStack output) {
		Fluid fluid = MineTweakerMC.getLiquidStack(output).getFluid();
		
		List<RefineryRecipe> toRemove = new ArrayList<RefineryRecipe>();
		for (RefineryRecipe recipe : ((RefineryRecipeManager) BuildcraftRecipes.refinery).getRecipes()) {
			if (recipe.getResult().getFluid() == fluid) {
				toRemove.add(recipe);
			}
		}
		
		for (RefineryRecipe recipe : toRemove) {
			MineTweakerAPI.apply(new RemoveRecipeAction(recipe));
		}
	}
	
	private static SortedSet<RefineryRecipe> getRecipes() {
		try {
			return (SortedSet<RefineryRecipe>) REFINERYRECIPEMANAGER_RECIPES.get((RefineryRecipeManager) BuildcraftRecipes.refinery);
		} catch (IllegalArgumentException ex) {
			Logger.getLogger(Refinery.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			Logger.getLogger(Refinery.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		throw new RuntimeException("Refinery recipes unavailable");
	}
	
	// ######################
	// ### Action Classes ###
	// ######################
	
	private static class AddRecipeAction implements IUndoableAction {
		private final ILiquidStack output;
		private final RefineryRecipe recipe;
		
		public AddRecipeAction(ILiquidStack output, int energyPerMB, int ticksPerMB, ILiquidStack input1, ILiquidStack input2) {
			this.output = output;
			
			RefineryRecipe rrecipe = null;
			try {
				rrecipe = (RefineryRecipe) CONSTRUCT_REFINERYRECIPE.newInstance(
						MineTweakerMC.getLiquidStack(input1),
						MineTweakerMC.getLiquidStack(input2),
						MineTweakerMC.getLiquidStack(output),
						energyPerMB,
						ticksPerMB);
			} catch (InstantiationException ex) {
				Logger.getLogger(Refinery.class.getName()).log(Level.SEVERE, null, ex);
			} catch (IllegalAccessException ex) {
				Logger.getLogger(Refinery.class.getName()).log(Level.SEVERE, null, ex);
			} catch (IllegalArgumentException ex) {
				Logger.getLogger(Refinery.class.getName()).log(Level.SEVERE, null, ex);
			} catch (InvocationTargetException ex) {
				Logger.getLogger(Refinery.class.getName()).log(Level.SEVERE, null, ex);
			}
			
			recipe = rrecipe;
		}

		@Override
		public void apply() {
			getRecipes().add(recipe);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			getRecipes().add(recipe);
		}

		@Override
		public String describe() {
			return "Adding refinery recipe for " + output;
		}

		@Override
		public String describeUndo() {
			return "Removing refinery recipe for " + output;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	private static class RemoveRecipeAction implements IUndoableAction {
		private final RefineryRecipe recipe;
		
		public RemoveRecipeAction(RefineryRecipe recipe) {
			this.recipe = recipe;
		}

		@Override
		public void apply() {
			getRecipes().remove(recipe);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			getRecipes().add(recipe);
		}

		@Override
		public String describe() {
			return "Removing refinery recipe for "
					+ recipe.getResult().getFluid().getLocalizedName(recipe.getResult());
		}

		@Override
		public String describeUndo() {
			return "Restoring refinery recipe for "
					+ recipe.getResult().getFluid().getLocalizedName(recipe.getResult());
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
}
