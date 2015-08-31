/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1710.recipes;

import minetweaker.api.recipes.ShapedRecipe;
import minetweaker.api.recipes.ShapelessRecipe;
import java.util.ArrayList;
import java.util.Arrays;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import static minetweaker.api.minecraft.MineTweakerMC.getItemStack;
import minetweaker.api.recipes.ICraftingRecipe;
import minetweaker.api.recipes.UnknownRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

/**
 *
 * @author Stan
 */
public class RecipeConverter {
	private RecipeConverter() {
	}

	public static final int TYPE_ADVANCED = 0;
	public static final int TYPE_ORE = 1;
	public static final int TYPE_BASIC = 2;

	private static int getIngredientType(IIngredient pattern) {
		Object internal = pattern.getInternal();
		if (internal == null) {
			return TYPE_ADVANCED;
		} else if (internal instanceof ItemStack) {
			return TYPE_BASIC;
		} else {
			return TYPE_ORE;
		}
	}

	private static int getRecipeType(IIngredient[] ingredients) {
		int type = TYPE_BASIC;
		for (IIngredient ingredient : ingredients) {
			type = Math.min(type, getIngredientType(ingredient));
		}
		return type;
	}

	public static IRecipe convert(ShapelessRecipe recipe) {
		IIngredient[] ingredients = recipe.getIngredients();
		int type = getRecipeType(ingredients);

		if (type == TYPE_BASIC) {
			ItemStack[] items = new ItemStack[ingredients.length];
			for (int i = 0; i < ingredients.length; i++) {
				items[i] = getItemStack(ingredients[i]);
			}
			return new ShapelessRecipeBasic(items, recipe);
		} else if (type == TYPE_ORE) {
			Object[] items = new Object[ingredients.length];
			for (int i = 0; i < ingredients.length; i++) {
				items[i] = ingredients[i].getInternal();
			}
			return new ShapelessRecipeOre(items, recipe);
		} else {
			return new ShapelessRecipeAdvanced(recipe);
		}
	}

	public static IRecipe convert(ShapedRecipe recipe) {
		IIngredient[] ingredients = recipe.getIngredients();
		byte[] posx = recipe.getIngredientsX();
		byte[] posy = recipe.getIngredientsY();

		// determine recipe type
		int type = getRecipeType(ingredients);

		// construct recipe
		if (type == TYPE_BASIC) {
			ItemStack[] basicIngredients = new ItemStack[recipe.getHeight() * recipe.getWidth()];
			for (int i = 0; i < ingredients.length; i++) {
				basicIngredients[posx[i] + posy[i] * recipe.getWidth()] = getItemStack(ingredients[i]);
			}

			return new ShapedRecipeBasic(basicIngredients, recipe);
		} else if (type == TYPE_ORE) {
			Object[] converted = new Object[recipe.getHeight() * recipe.getWidth()];
			for (int i = 0; i < ingredients.length; i++) {
				converted[posx[i] + posy[i] * recipe.getWidth()] = ingredients[i].getInternal();
			}

			// arguments contents:
			// 1) recipe patterns
			// 2) characters + ingredients

			int counter = 0;
			String[] parts = new String[recipe.getHeight()];
			ArrayList rarguments = new ArrayList();
			for (int i = 0; i < recipe.getHeight(); i++) {
				char[] pattern = new char[recipe.getWidth()];
				for (int j = 0; j < recipe.getWidth(); j++) {
					int off = i * recipe.getWidth() + j;
					if (converted[off] == null) {
						pattern[j] = ' ';
					} else {
						pattern[j] = (char) ('A' + counter);
						rarguments.add(pattern[j]);
						rarguments.add(converted[off]);
						counter++;
					}
				}
				parts[i] = new String(pattern);
			}

			rarguments.addAll(0, Arrays.asList(parts));

			return new ShapedRecipeOre(rarguments.toArray(), recipe);
		} else {
			return new ShapedRecipeAdvanced(recipe);
		}
	}

	public static ICraftingRecipe toCraftingRecipe(IRecipe recipe) {
		IItemStack output = MineTweakerMC.getIItemStack(recipe.getRecipeOutput());

		if (recipe instanceof ShapelessRecipes) {
			ShapelessRecipes shapeless = (ShapelessRecipes) recipe;

			IIngredient[] ingredients = new IIngredient[shapeless.recipeItems.size()];
			for (int i = 0; i < ingredients.length; i++) {
				ingredients[i] = MineTweakerMC.getIIngredient(shapeless.recipeItems.get(i));
			}

			return new ShapelessRecipe(output, ingredients, null);
		} else if (recipe instanceof ShapedRecipes) {
			ShapedRecipes shaped = (ShapedRecipes) recipe;

			IIngredient[][] ingredients = new IIngredient[shaped.recipeHeight][shaped.recipeWidth];
			for (int i = 0; i < shaped.recipeHeight; i++) {
				for (int j = 0; j < shaped.recipeWidth; j++) {
					ingredients[i][j] = MineTweakerMC.getIItemStack(shaped.recipeItems[i * shaped.recipeWidth + j]);
				}
			}

			return new ShapedRecipe(output, ingredients, null, false);
		} else if (recipe instanceof ShapedOreRecipe) {
			ShapedOreRecipe shaped = (ShapedOreRecipe) recipe;

			int width = (int) Math.sqrt(recipe.getRecipeSize());
			int height = recipe.getRecipeSize() / width;

			IIngredient[][] recipeIngredients = new IIngredient[height][width];
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					recipeIngredients[i][j] = MineTweakerMC.getIIngredient(shaped.getInput()[i * width + j]);
				}
			}

			return new ShapedRecipe(output, recipeIngredients, null, false);
		} else if (recipe instanceof ShapelessOreRecipe) {
			ShapelessOreRecipe shapeless = (ShapelessOreRecipe) recipe;

			IIngredient[] ingredients = new IIngredient[shapeless.getRecipeSize()];
			for (int i = 0; i < ingredients.length; i++) {
				ingredients[i] = MineTweakerMC.getIIngredient(shapeless.getInput().get(i));
			}

			return new ShapelessRecipe(output, ingredients, null);
		} else {
			return new UnknownRecipe(output);
		}
	}
}
