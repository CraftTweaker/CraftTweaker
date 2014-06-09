/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.minecraft.recipes;

import java.util.HashMap;
import java.util.Map;
import minetweaker.minecraft.item.IIngredient;
import minetweaker.minecraft.item.IItemStack;

/**
 *
 * @author Stan
 */
public class ShapedRecipe {
	private final int width;
	private final int height;
	private final byte[] posx;
	private final byte[] posy;
	private final boolean mirrored;
	private final IRecipeFunction function;
	
	private final IItemStack output;
	private final IIngredient[] ingredients;
	
	public ShapedRecipe(IItemStack output, IIngredient[][] ingredients, IRecipeFunction function, boolean mirrored) {
		int numIngredients = 0;
		for (IIngredient[] row : ingredients) {
			for (IIngredient ingredient : row) {
				if (ingredient != null) {
					numIngredients++;
				}
			}
		}
		
		this.posx = new byte[numIngredients];
		this.posy = new byte[numIngredients];
		this.output = output;
		this.ingredients = new IIngredient[numIngredients];
		this.function = function;
		
		int width1 = 0;
		int height1 = ingredients.length;
		
		int ix = 0;
		for (int j = 0; j < ingredients.length; j++) {
			IIngredient[] row = ingredients[j];
			width1 = Math.max(width1, row.length);
			
			for (int i = 0; i < row.length; i++) {
				if (row[i] != null) {
					this.posx[ix] = (byte) i;
					this.posy[ix] = (byte) j;
					this.ingredients[ix] = row[i];
					ix++;
				}
			}
		}
		
		this.width = width1;
		this.height = height1;
		this.mirrored = mirrored;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public boolean isMirrored() {
		return mirrored;
	}
	
	public IIngredient[] getIngredients() {
		return ingredients;
	}
	
	public byte[] getIngredientsX() {
		return posx;
	}
	
	public byte[] getIngredientsY() {
		return posy;
	}
	
	public IItemStack getOutput() {
		return output;
	}
	
	public boolean matches(ICraftingInventory inventory) {
		int numItems = 0;
		for (int i = 0; i < inventory.getSize(); i++) {
			if (inventory.getStack(i) != null) {
				numItems++;
			}
		}
		
		if (numItems != ingredients.length) {
			return false;
		}
		
		for (int i = 0; i < inventory.getWidth() - width; i++) {
			out: for (int j = 0; j < inventory.getHeight() - height; j++) {
				for (int k = 0; k < ingredients.length; k++) {
					IItemStack item = inventory.getStack(posx[k] + i, posy[k] + j);
					if (item == null) continue out;
					
					if (!ingredients[k].matches(item)) continue out;
				}
				
				return true;
			}
		}
		
		if (mirrored) {
			for (int i = 0; i < inventory.getWidth() - width; i++) {
				out: for (int j = 0; j < inventory.getHeight() - height; j++) {
					for (int k = 0; k < ingredients.length; k++) {
						IItemStack item = inventory.getStack(inventory.getWidth() - (posx[k] + i), posy[k] + j);
						if (item == null) continue out;
						
						if (!ingredients[k].matches(item)) continue out;
					}

					return true;
				}
			}
		}
		
		return false;
	}
	
	public IItemStack getCraftingResult(ICraftingInventory inventory) {
		IItemStack[] stacks = new IItemStack[ingredients.length];
		
		for (int i = 0; i < inventory.getWidth() - width; i++) {
			out: for (int j = 0; j < inventory.getHeight() - height; j++) {
				for (int k = 0; k < ingredients.length; k++) {
					IItemStack item = inventory.getStack(posx[k] + i, posy[k] + j);
					if (item == null) continue out;
					
					if (!ingredients[k].matches(item)) continue out;
					stacks[k] = item;
				}
				
				return doRecipe(inventory, stacks, i, j, output, false);
			}
		}
		
		if (mirrored) {
			for (int i = 0; i < inventory.getWidth() - width; i++) {
				out: for (int j = 0; j < inventory.getHeight() - height; j++) {
					for (int k = 0; k < ingredients.length; k++) {
						IItemStack item = inventory.getStack(inventory.getWidth() - (posx[k] + i), posy[k] + j);
						if (item == null) continue out;
						
						if (!ingredients[k].matches(item)) continue out;
						stacks[k] = item;
					}

					return doRecipe(inventory, stacks, i, j, output, true);
				}
			}
		}
		
		return null;
	}
	
	private IItemStack doRecipe(
			ICraftingInventory inventory,
			IItemStack[] stacks,
			int offx, int offy,
			IItemStack output,
			boolean mirrored) {
		// determine output and apply transformations
		
		if (function != null) {
			Map<String, IItemStack> tagged = new HashMap<String, IItemStack>();
			for (int k = 0; k < ingredients.length; k++) {
				if (ingredients[k].getMark() != null) {
					tagged.put(ingredients[k].getMark(), stacks[k]);
				}
			}

			output = function.process(output, tagged);
		}
		
		if (output == null) {
			return null;
		}
		
		for (int i = 0; i < ingredients.length; i++) {
			IItemStack transformed = ingredients[i].applyTransform(stacks[i]);
			if (transformed != stacks[i]) {
				if (mirrored) {
					inventory.setStack(
							inventory.getWidth() - (offx + posx[i]),
							offy + posy[i],
							transformed);
				} else {
					inventory.setStack(
							offx + posx[i],
							offy + posy[i],
							transformed);
				}
			}
		}
		
		return output;
	}
}
