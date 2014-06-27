/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mods.ic2;

import ic2.api.recipe.IRecipeInput;
import java.util.ArrayList;
import java.util.List;
import minetweaker.mc172.item.MCItemStack;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;

/**
 * Wrapper class for ITweakerItemStackPatterns to IC2 recipe inputs.
 * 
 * @author Stan Hebben
 */
public class IC2RecipeInput implements IRecipeInput {
	private final IIngredient ingredient;
	
	public IC2RecipeInput(IIngredient ingredient) {
		this.ingredient = ingredient;
	}

	@Override
	public boolean matches(ItemStack subject) {
		return ingredient.matches(new MCItemStack(subject));
	}

	@Override
	public int getAmount() {
		return ingredient.getAmount();
	}

	@Override
	public List<ItemStack> getInputs() {
		List<ItemStack> items = new ArrayList<ItemStack>();
		for (IItemStack item : ingredient.getItems()) {
			items.add((ItemStack) item.getInternal());
		}
		return items;
	}
}
