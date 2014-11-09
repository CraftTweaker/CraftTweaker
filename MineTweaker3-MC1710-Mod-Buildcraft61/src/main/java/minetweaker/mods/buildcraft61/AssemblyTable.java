
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mods.buildcraft61;

import buildcraft.api.recipes.BuildcraftRecipeRegistry;
import buildcraft.api.recipes.IFlexibleRecipe;
import buildcraft.core.recipes.FlexibleRecipe;
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
@ZenClass("mods.buildcraft.AssemblyTable")
@ModOnly(value="BuildCraft|Core", version="6.1")
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
		List<IFlexibleRecipe<ItemStack>> toRemove = BuildcraftRecipes.removeRecipes(
				output,
				ingredients,
				BuildcraftRecipeRegistry.assemblyTable.getRecipes());
		
		for (IFlexibleRecipe<ItemStack> recipe : toRemove) {
			ItemStack recipeOutput = recipe instanceof FlexibleRecipe ? (ItemStack)(((FlexibleRecipe) recipe).output) : null;
			MineTweakerAPI.apply(new RemoveRecipeAction(recipe, recipeOutput));
		}
	}
	
	// ######################
	// ### Action classes ###
	// ######################
	
	private static class AddRecipeAction implements IUndoableAction {
		private final IItemStack output;
		private final IFlexibleRecipe<ItemStack> recipe;
		
		public AddRecipeAction(IItemStack output, double energy, IIngredient[] ingredients) {
			this.output = output;
			this.recipe = new MTFlexibleRecipe(output, ingredients, (int) energy, 0);
		}

		@Override
		public void apply() {
			BuildcraftRecipeRegistry.assemblyTable.addRecipe(recipe);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			BuildcraftRecipeRegistry.assemblyTable.removeRecipe(recipe);
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
		private final IFlexibleRecipe<ItemStack> recipe;
		private final ItemStack output;
		
		public RemoveRecipeAction(IFlexibleRecipe<ItemStack> recipe, ItemStack output) {
			this.recipe = recipe;
			this.output = output;
		}

		@Override
		public void apply() {
			BuildcraftRecipeRegistry.assemblyTable.removeRecipe(recipe);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			BuildcraftRecipeRegistry.assemblyTable.addRecipe(recipe);
		}

		@Override
		public String describe() {
			return "Removing assembly table recipe for " + MineTweakerMC.getIItemStack(output);
		}

		@Override
		public String describeUndo() {
			return "Restoring assembly table recipe for " + MineTweakerMC.getIItemStack(output);
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}
	}
}
