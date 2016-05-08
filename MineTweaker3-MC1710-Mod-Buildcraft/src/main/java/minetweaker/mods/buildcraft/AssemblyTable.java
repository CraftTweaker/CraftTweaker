
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mods.buildcraft;

import buildcraft.api.recipes.BuildcraftRecipes;
import buildcraft.core.recipes.AssemblyRecipeManager;
import buildcraft.core.recipes.AssemblyRecipeManager.AssemblyRecipe;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author Stan
 */
@ZenClass("mods.buildcraft.AssemblyTable")
@ModOnly(value="BuildCraft|Core", version="6.0")
public class AssemblyTable {
	@ZenMethod
	public static void addRecipe(IItemStack output, double energy, IIngredient[] ingredients) {
		MineTweakerAPI.apply(new AddRecipeAction(output, energy, ingredients));
	}
	
	@ZenMethod
	public static void remove(IIngredient output) {
		removeRecipe(output, null, false);
	}
	
	@ZenMethod
	public static void removeRecipe(IIngredient output, @Optional IIngredient[] ingredients, @Optional boolean wildcard) {
		List<AssemblyRecipe> toRemove = new ArrayList<AssemblyRecipe>();
		
		outer: for (AssemblyRecipe recipe : ((AssemblyRecipeManager) BuildcraftRecipes.assemblyTable).getRecipes()) {
			if (output.matches(MineTweakerMC.getIItemStack(recipe.getOutput()))
					&& ingredientsMatch(recipe, ingredients, wildcard)) {
				toRemove.add(recipe);
			}
		}
		
		for (AssemblyRecipe recipe : toRemove) {
			MineTweakerAPI.apply(new RemoveRecipeAction(recipe));
		}
	}
	
	/**
	 * Checks if ingredients from the recipe match with the given ingredients.
	 * 
	 * @param recipe recipe to check
	 * @param ingredients required ingredients
	 * @param wildcard true if there can be more ingredients in the recipe than given, false otherwise
	 * @return matching result
	 */
	private static boolean ingredientsMatch(AssemblyRecipe recipe, IIngredient[] ingredients, boolean wildcard) {
		if (ingredients == null)
			return true;
		
		int matchedIngredients = 0;
		boolean[] matched = new boolean[ingredients.length];
		checkIngredient: for (int i = 0; i < recipe.getInputs().length; i++) {
			Object ingredientObject = recipe.getInputs()[i];
			if (ingredientObject instanceof Number)
				continue;
			
			IIngredient recipeIngredient = MineTweakerMC.getIIngredient(recipe.getInputs()[i]);
			
			for (int j = 0; j < ingredients.length; j++) {
				if (!matched[j] && ingredients[j].contains(recipeIngredient)) {
					matched[j] = true;
					matchedIngredients++;
					continue checkIngredient;
				}
			}
			
			if (!wildcard)
				return false;
		}
		
		return matchedIngredients == ingredients.length;
	}
	
	// ######################
	// ### Action classes ###
	// ######################
	
	private static class AddRecipeAction implements IUndoableAction {
		private final IItemStack output;
		private final AssemblyRecipe recipe;
		
		public AddRecipeAction(IItemStack output, double energy, IIngredient[] ingredients) {
			this.output = output;
			
			Object[] mcIngredients = new Object[ingredients.length];
			for (int i = 0; i < ingredients.length; i++) {
				mcIngredients[i] = ingredients[i].getInternal();
				if (mcIngredients[i] == null) {
					throw new IllegalArgumentException("Not a valid assembly table ingredient");
				}
			}
			
			recipe = new AssemblyRecipe(MineTweakerMC.getItemStack(output), energy, mcIngredients);
		}

		@Override
		public void apply() {
			((AssemblyRecipeManager) BuildcraftRecipes.assemblyTable).getRecipes().add(recipe);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			BuildcraftRecipes.assemblyTable.getRecipes().remove(recipe);
		}

		@Override
		public String describe() {
			return "Adding assembly table recipe for " + output;
		}

		@Override
		public String describeUndo() {
			return "Removing assembly table recipe for " + output;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
	
	private static class RemoveRecipeAction implements IUndoableAction {
		private final AssemblyRecipe recipe;
		
		public RemoveRecipeAction(AssemblyRecipe recipe) {
			this.recipe = recipe;
		}

		@Override
		public void apply() {
			BuildcraftRecipes.assemblyTable.getRecipes().remove(recipe);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			((AssemblyRecipeManager) BuildcraftRecipes.assemblyTable).getRecipes().add(recipe);
		}

		@Override
		public String describe() {
			return "Removing assembly table recipe for " + MineTweakerMC.getIItemStack(recipe.getOutput());
		}

		@Override
		public String describeUndo() {
			return "Restoring assembly table recipe for " + MineTweakerMC.getIItemStack(recipe.getOutput());
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
}
