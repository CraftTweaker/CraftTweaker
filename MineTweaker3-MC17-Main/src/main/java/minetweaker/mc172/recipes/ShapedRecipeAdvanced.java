/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc172.recipes;

import minetweaker.api.recipes.ShapedRecipe;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

/**
 * TODO: NEI integration for this kind of recipes.
 * 
 * @author Stan
 */
public class ShapedRecipeAdvanced implements IRecipe {
	private final ShapedRecipe recipe;
	
	public ShapedRecipeAdvanced(ShapedRecipe recipe) {
		this.recipe = recipe;
	}

	@Override
	public boolean matches(InventoryCrafting inventory, World world) {
		return recipe.matches(MCCraftingInventory.get(inventory));
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventory) {
		return ((ItemStack) recipe.getCraftingResult(MCCraftingInventory.get(inventory)).getInternal()).copy();
	}

	@Override
	public int getRecipeSize() {
		return recipe.getWidth() * recipe.getHeight();
	}

	@Override
	public ItemStack getRecipeOutput() {
		return (ItemStack) recipe.getOutput().getInternal();
	}
}
