/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mods.ic2.machines;

import ic2.api.recipe.RecipeOutput;
import ic2.api.recipe.Recipes;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.ModOnly;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import static minetweaker.api.minecraft.MineTweakerMC.getIItemStack;
import static minetweaker.api.minecraft.MineTweakerMC.getItemStack;
import static minetweaker.api.minecraft.MineTweakerMC.getItemStacks;
import minetweaker.mods.ic2.IC2RecipeInput;
import minetweaker.mods.ic2.MachineAddRecipeAction;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 *
 * @author Stan Hebben
 */
@ZenClass("mods.ic2.MetalFormer")
@ModOnly("IC2")
public class MetalFormer {
	/**
	 * Adds an extruding recipe to the metal former.
	 * 
	 * @param output output item
	 * @param input input item
	 */
	@ZenMethod
	public static void addExtrudingRecipe(IItemStack output, IIngredient input) {
		MineTweakerAPI.apply(new MachineAddRecipeAction(
				"metal former - extruding",
				Recipes.metalformerExtruding,
				getItemStacks(output),
				null,
				new IC2RecipeInput(input)));
	}
	
	/**
	 * Gets the extruding output for the given input.
	 * 
	 * @param input input item
	 * @return output item, or null
	 */
	@ZenMethod
	public static IItemStack getExtrudingOutput(IItemStack input) {
		RecipeOutput output = Recipes.metalformerExtruding.getOutputFor(getItemStack(input), false);
		if (output == null || output.items.isEmpty()) return null;
		return getIItemStack(output.items.get(0));
	}
	
	/**
	 * Adds a rolling recipe to the metal former.
	 * 
	 * @param output output item
	 * @param input input item
	 */
	@ZenMethod
	public static void addRollingRecipe(IItemStack output, IIngredient input) {
		MineTweakerAPI.apply(new MachineAddRecipeAction(
				"metal former - rolling",
				Recipes.metalformerRolling,
				getItemStacks(output),
				null,
				new IC2RecipeInput(input)));
	}
	
	/**
	 * Gets the extruding output for the given input.
	 * 
	 * @param input input item
	 * @return output item, or null
	 */
	@ZenMethod
	public static IItemStack getRollingOutput(IItemStack input) {
		RecipeOutput output = Recipes.metalformerRolling.getOutputFor(getItemStack(input), false);
		if (output == null || output.items.isEmpty()) return null;
		return getIItemStack(output.items.get(0));
	}
	/**
	 * Adds a extruding recipe to the metal former.
	 * 
	 * @param output output item
	 * @param input input item
	 */
	@ZenMethod
	public static void addCuttingRecipe(IItemStack output, IIngredient input) {
		MineTweakerAPI.apply(new MachineAddRecipeAction(
				"metal former - cutting",
				Recipes.metalformerCutting,
				getItemStacks(output),
				null,
				new IC2RecipeInput(input)));
	}
	
	/**
	 * Gets the cutting output for the given input.
	 * 
	 * @param input input item
	 * @return output item, or null
	 */
	@ZenMethod
	public static IItemStack getCuttingOutput(IItemStack input) {
		RecipeOutput output = Recipes.metalformerCutting.getOutputFor(getItemStack(input), false);
		if (output == null || output.items.isEmpty()) return null;
		return getIItemStack(output.items.get(0));
	}
}
