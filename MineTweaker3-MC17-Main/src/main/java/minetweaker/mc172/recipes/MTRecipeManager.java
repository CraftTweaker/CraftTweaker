/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc172.recipes;

import minetweaker.minecraft.recipes.ShapedRecipe;
import minetweaker.minecraft.recipes.ShapelessRecipe;
import java.util.ArrayList;
import java.util.List;
import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.mc172.item.TweakerItemStack;
import minetweaker.mc172.util.MineTweakerHacks;import minetweaker.minecraft.item.IIngredient;
import minetweaker.minecraft.item.IItemStack;
;
import minetweaker.minecraft.recipes.IRecipeFunction;
import minetweaker.minecraft.recipes.IRecipeManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

/**
 *
 * @author Stan
 */
public class MTRecipeManager implements IRecipeManager {
	private final List<IRecipe> recipes;
	
	public MTRecipeManager() {
		recipes = MineTweakerHacks.getRecipes();
	}
	
	@Override
	public int remove(IIngredient output) {
		List<IRecipe> toRemove = new ArrayList<IRecipe>();
		List<Integer> removeIndex = new ArrayList<Integer>();
		for (int i = 0; i < recipes.size(); i++) {
			IRecipe recipe = recipes.get(i);

			if (output.matches(new TweakerItemStack(recipe.getRecipeOutput()))) {
				toRemove.add(recipe);
				removeIndex.add(i);
			}
		}

		MineTweakerAPI.tweaker.apply(new ActionRemoveRecipes(toRemove, removeIndex));
		return toRemove.size();
	}

	@Override
	public void addShaped(IItemStack output, IIngredient[][] ingredients, IRecipeFunction function, boolean mirrored) {
		if (output.getInternal() == null) {
			MineTweakerAPI.logger.logError("invalid output item");
			return;
		}
		
		ShapedRecipe recipe = new ShapedRecipe(output, ingredients, function, mirrored);
		IRecipe irecipe = RecipeConverter.convert(recipe);
		MineTweakerAPI.tweaker.apply(new ActionAddRecipe(irecipe));
	}

	@Override
	public void addShapeless(IItemStack output, IIngredient[] ingredients, IRecipeFunction function) {
		if (output.getInternal() == null) {
			MineTweakerAPI.logger.logError("invalid output item");
			return;
		}
		
		ShapelessRecipe recipe = new ShapelessRecipe(output, ingredients, function);
		IRecipe irecipe = RecipeConverter.convert(recipe);
		MineTweakerAPI.tweaker.apply(new ActionAddRecipe(irecipe));
	}

	@Override
	public int removeShaped(IIngredient output, IIngredient[][] ingredients) {
		List<IRecipe> toRemove = new ArrayList<IRecipe>();
		List<Integer> removeIndex = new ArrayList<Integer>();
		for (int i = 0; i < recipes.size(); i++) {
			IRecipe recipe = recipes.get(i);
			
			if (!output.matches(new TweakerItemStack(recipe.getRecipeOutput()))) {
				continue;
			}
			
			if (recipe instanceof ShapedRecipes) {
				ShapedRecipes srecipe = (ShapedRecipes) recipe;
				// TODO: check contents
			} else if (recipe instanceof ShapedOreRecipe) {
				ShapedOreRecipe srecipe = (ShapedOreRecipe) recipe;
				// TODO: check contents
			}
			
			toRemove.add(recipe);
			removeIndex.add(i);
		}

		MineTweakerAPI.tweaker.apply(new ActionRemoveRecipes(toRemove, removeIndex));
		return toRemove.size();
	}

	@Override
	public int removeShapeless(IIngredient output, IIngredient[] ingredients, boolean wildcard) {
		List<IRecipe> toRemove = new ArrayList<IRecipe>();
		List<Integer> removeIndex = new ArrayList<Integer>();
		for (int i = 0; i < recipes.size(); i++) {
			IRecipe recipe = recipes.get(i);
			
			if (!output.matches(new TweakerItemStack(recipe.getRecipeOutput()))) {
				continue;
			}
			
			if (recipe instanceof ShapelessRecipes) {
				ShapelessRecipes srecipe = (ShapelessRecipes) recipe;
				// TODO: check contents
			} else if (recipe instanceof ShapelessOreRecipe) {
				ShapelessOreRecipe srecipe = (ShapelessOreRecipe) recipe;
				ArrayList<Object> inputs = srecipe.getInput();
				if (inputs.size() < ingredients.length) {
					continue;
				} if (!wildcard && inputs.size() > ingredients.length) {
					continue;
				}
				
				boolean[] tag = new boolean[ingredients.length];
				for (Object object : inputs) {
					
				}
				
				// TODO: check contents
			}
			
			toRemove.add(recipe);
			removeIndex.add(i);
		}

		MineTweakerAPI.tweaker.apply(new ActionRemoveRecipes(toRemove, removeIndex));
		return toRemove.size();
	}

	@Override
	public IItemStack craft(IItemStack[][] contents) {
		return null; // TODO: implement
	}
	
	private class ActionRemoveRecipes implements IUndoableAction {
		private final List<Integer> removingIndices;
		private final List<IRecipe> removingRecipes;
		
		public ActionRemoveRecipes(List<IRecipe> recipes, List<Integer> indices) {
			this.removingIndices = indices;
			this.removingRecipes = recipes;
		}

		@Override
		public void apply() {
			for (int i = removingIndices.size() - 1; i >= 0; i--) {
				recipes.remove((int) removingIndices.get(i));
			}
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			for (int i = 0; i < removingIndices.size(); i++) {
				recipes.add(removingIndices.get(i), removingRecipes.get(i));
			}
		}

		@Override
		public String describe() {
			return "Removing " + removingIndices.size() + " recipes";
		}

		@Override
		public String describeUndo() {
			return "Restoring " + removingIndices.size() + " recipes";
		}
	}
	
	private class ActionAddRecipe implements IUndoableAction {
		private final IRecipe recipe;
		
		public ActionAddRecipe(IRecipe recipe) {
			this.recipe = recipe;
		}

		@Override
		public void apply() {
			recipes.add(recipe);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			recipes.remove(recipe);
		}

		@Override
		public String describe() {
			return "Adding recipe for " + recipe.getRecipeOutput().getDisplayName();
		}

		@Override
		public String describeUndo() {
			return "Removing recipe for " + recipe.getRecipeOutput().getDisplayName();
		}
	}
}
