/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mods.buildcraft;

import buildcraft.api.recipes.BuildcraftRecipes;
import buildcraft.api.recipes.IIntegrationRecipeManager.IIntegrationRecipe;
import java.util.ArrayList;
import java.util.List;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author Stan
 */
@ZenClass("mods.buildcraft.IntegrationTable")
@ModOnly("BuildCraft|Core")
public class IntegrationTable {
	@ZenMethod
	public static void addRecipe(IItemStack output, double energy, IIngredient inputA, IIngredient inputB, @Optional IIntegrationRecipeFunction function) {
		MineTweakerAPI.apply(new AddRecipeAction(output, energy, inputA, inputB, function));
	}
	
	@ZenMethod
	public static void remove(IIngredient output) {
		removeRecipe(output, null, null);
	}
	
	@ZenMethod
	public static void removeRecipe(IIngredient output, @Optional IIngredient inputA, @Optional IIngredient inputB) {
		List<IIntegrationRecipe> toRemove = new ArrayList<IIntegrationRecipe>();
		
		outer: for (IIntegrationRecipe recipe : BuildcraftRecipes.integrationTable.getRecipes()) {
			for (ItemStack component : recipe.getComponents()) {
				if (!output.matches(MineTweakerMC.getIItemStack(component)))
					continue outer;
				
				if (inputA != null) {
					for (ItemStack example1 : recipe.getExampleInputsA()) {
						if (!inputA.matches(MineTweakerMC.getIItemStack(example1))) {
							continue outer;
						}
					}
				}
				
				if (inputB != null) {
					for (ItemStack example2 : recipe.getExampleInputsB()) {
						if (!inputB.matches(MineTweakerMC.getIItemStack(example2))) {
							continue outer;
						}
					}
				}
				
				toRemove.add(recipe);
			}
		}
		
		for (IIntegrationRecipe recipe : toRemove) {
			MineTweakerAPI.apply(new RemoveRecipeAction(recipe));
		}
	}
	
	// ######################
	// ### Action Classes ###
	// ######################
	
	private static class AddRecipeAction implements IUndoableAction {
		private final MTIntegrationRecipe recipe;
		
		public AddRecipeAction(IItemStack output, double energy, IIngredient inputA, IIngredient inputB, IIntegrationRecipeFunction function) {
			recipe = new MTIntegrationRecipe(output, energy, inputA, inputB, function);
		}

		@Override
		public void apply() {
			BuildcraftRecipes.integrationTable.addRecipe(recipe);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			BuildcraftRecipes.integrationTable.getRecipes().remove(recipe);
		}

		@Override
		public String describe() {
			return "Adding integration table recipe for " + recipe.output;
		}

		@Override
		public String describeUndo() {
			return "Removing integration table recipe for " + recipe.output;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	private static class RemoveRecipeAction implements IUndoableAction {
		private final IIntegrationRecipe recipe;
		
		public RemoveRecipeAction(IIntegrationRecipe recipe) {
			this.recipe = recipe;
		}
		
		@Override
		public void apply() {
			BuildcraftRecipes.integrationTable.getRecipes().remove(recipe);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			BuildcraftRecipes.integrationTable.addRecipe(recipe);
		}

		@Override
		public String describe() {
			return "Removing integration table recipe for " + MineTweakerMC.getIItemStack(recipe.getComponents()[0]);
		}

		@Override
		public String describeUndo() {
			return "Restoring integration table recipe for " + MineTweakerMC.getIItemStack(recipe.getComponents()[0]);
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	private static class MTIntegrationRecipe implements IIntegrationRecipe {
		private final IItemStack output;
		private final ItemStack[] components;
		private final double energy;
		private final IIngredient inputA;
		private final ItemStack[] inputAExamples;
		private final IIngredient inputB;
		private final ItemStack[] inputBExamples;
		private final IIntegrationRecipeFunction function;
		
		private MTIntegrationRecipe(IItemStack output, double energy, IIngredient inputA, IIngredient inputB, IIntegrationRecipeFunction function) {
			this.output = output;
			this.components = new ItemStack[] { MineTweakerMC.getItemStack(output) };
			this.energy = energy;
			this.inputA = inputA;
			this.inputAExamples = MineTweakerMC.getExamples(inputA);
			this.inputB = inputB;
			this.inputBExamples = MineTweakerMC.getExamples(inputB);
			this.function = function;
		}

		@Override
		public double getEnergyCost() {
			return energy;
		}

		@Override
		public boolean isValidInputA(ItemStack is) {
			return inputA.matches(MineTweakerMC.getIItemStack(is));
		}

		@Override
		public boolean isValidInputB(ItemStack is) {
			return inputB.matches(MineTweakerMC.getIItemStack(is));
		}

		@Override
		public ItemStack getOutputForInputs(ItemStack is, ItemStack is1, ItemStack[] iss) {
			if (function != null) {
				IItemStack actualInputA = MineTweakerMC.getIItemStack(is);
				IItemStack actualInputB = MineTweakerMC.getIItemStack(is1);
				return MineTweakerMC.getItemStack(function.recipe(output, actualInputA, actualInputB));
			} else {
				return MineTweakerMC.getItemStack(output);
			}
		}

		@Override
		public ItemStack[] getComponents() {
			return components;
		}

		@Override
		public ItemStack[] getExampleInputsA() {
			return inputAExamples;
		}

		@Override
		public ItemStack[] getExampleInputsB() {
			return inputBExamples;
		}
	}
}
