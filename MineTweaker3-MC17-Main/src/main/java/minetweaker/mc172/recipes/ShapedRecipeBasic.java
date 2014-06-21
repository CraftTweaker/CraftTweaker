/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc172.recipes;

import minetweaker.api.item.IItemStack;
import minetweaker.api.recipes.ShapedRecipe;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.world.World;

/**
 *
 * @author Stan
 */
public class ShapedRecipeBasic extends ShapedRecipes {
	private final ShapedRecipe recipe;
	
	public ShapedRecipeBasic(ItemStack[] basicInputs, ShapedRecipe recipe) {
		super(recipe.getWidth(), recipe.getHeight(), basicInputs, (ItemStack) recipe.getOutput().getInternal());
		
		this.recipe = recipe;
	}
	
	@Override
    public boolean matches(InventoryCrafting inventory, World world) {
		return recipe.matches(TweakerCraftingInventory.get(inventory));
	}

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventory) {
		IItemStack result = recipe.getCraftingResult(TweakerCraftingInventory.get(inventory));
		if (result == null) {
			return null;
		} else {
			return ((ItemStack) result.getInternal()).copy();
		}
	}
}
