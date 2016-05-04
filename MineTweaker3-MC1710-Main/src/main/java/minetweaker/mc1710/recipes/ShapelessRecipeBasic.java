/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1710.recipes;

import minetweaker.api.recipes.ShapelessRecipe;
import java.util.Arrays;
import static minetweaker.api.minecraft.MineTweakerMC.getItemStack;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.world.World;

/**
 *
 * @author Stan
 */
public class ShapelessRecipeBasic extends ShapelessRecipes {
	private final ShapelessRecipe recipe;

	public ShapelessRecipeBasic(ItemStack[] ingredients, ShapelessRecipe recipe) {
		super(getItemStack(recipe.getOutput()), Arrays.asList(ingredients));

		this.recipe = recipe;
	}

	@Override
	public boolean matches(InventoryCrafting inventory, World world) {
		return recipe.matches(MCCraftingInventory.get(inventory));
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventory) {
		return getItemStack(recipe.getCraftingResult(MCCraftingInventory.get(inventory))).copy();
	}
}
