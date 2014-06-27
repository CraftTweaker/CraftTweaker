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
import minetweaker.mc172.item.MCItemStack;
import minetweaker.mc172.util.MineTweakerUtil;
import minetweaker.mods.ic2.IC2RecipeInput;
import minetweaker.mods.ic2.MachineAddRecipeAction;
import net.minecraft.item.ItemStack;
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
		MineTweakerAPI.tweaker.apply(new MachineAddRecipeAction(
				"metal former - extruding",
				Recipes.metalformerExtruding,
				MineTweakerUtil.getItemStacks(output),
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
		RecipeOutput output = Recipes.metalformerExtruding.getOutputFor((ItemStack) input.getInternal(), false);
		if (output == null || output.items.size() > 0) return null;
		return new MCItemStack(output.items.get(0));
	}
	
	/**
	 * Adds a rolling recipe to the metal former.
	 * 
	 * @param output output item
	 * @param input input item
	 */
	@ZenMethod
	public static void addRollingRecipe(IItemStack output, IIngredient input) {
		MineTweakerAPI.tweaker.apply(new MachineAddRecipeAction(
				"metal former - rolling",
				Recipes.metalformerRolling,
				new ItemStack[] { (ItemStack) output.getInternal() },
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
		RecipeOutput output = Recipes.metalformerRolling.getOutputFor((ItemStack) input.getInternal(), false);
		if (output == null || output.items.size() > 0) return null;
		return new MCItemStack(output.items.get(0));
	}
	/**
	 * Adds a extruding recipe to the metal former.
	 * 
	 * @param output output item
	 * @param input input item
	 */
	@ZenMethod
	public static void addCuttingRecipe(IItemStack output, IIngredient input) {
		MineTweakerAPI.tweaker.apply(new MachineAddRecipeAction(
				"metal former - cutting",
				Recipes.metalformerCutting,
				new ItemStack[] { (ItemStack) output.getInternal() },
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
		RecipeOutput output = Recipes.metalformerCutting.getOutputFor((ItemStack) input.getInternal(), false);
		if (output == null || output.items.size() > 0) return null;
		return new MCItemStack(output.items.get(0));
	}
}
