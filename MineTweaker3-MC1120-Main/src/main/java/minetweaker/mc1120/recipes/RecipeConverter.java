package minetweaker.mc1120.recipes;

import minetweaker.api.item.*;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.api.recipes.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.util.*;
import net.minecraftforge.oredict.*;

import java.util.*;

import static minetweaker.api.minecraft.MineTweakerMC.getItemStack;

/**
 * @author Stan
 */
public class RecipeConverter {

    public static final int TYPE_ADVANCED = 0;
    public static final int TYPE_ORE = 1;
    public static final int TYPE_BASIC = 2;

    private RecipeConverter() {
    }

    private static int getIngredientType(IIngredient pattern) {
        Object internal = pattern.getInternal();
        if(internal == null) {
            return TYPE_ADVANCED;
        } else if(internal instanceof ItemStack) {
            return TYPE_BASIC;
        } else {
            return TYPE_ORE;
        }
    }

    private static int getRecipeType(IIngredient[] ingredients) {
        int type = TYPE_BASIC;
        for(IIngredient ingredient : ingredients) {
            type = Math.min(type, getIngredientType(ingredient));
        }
        return type;
    }

    public static IRecipe convert(ShapelessRecipe recipe) {
        IIngredient[] ingredients = recipe.getIngredients();
        int type = getRecipeType(ingredients);

        if(type == TYPE_BASIC) {
            NonNullList<Ingredient> items = NonNullList.withSize(ingredients.length, Ingredient.EMPTY);
            for(int i = 0; i < ingredients.length; i++) {
                items.set(i, Ingredient.fromStacks(getItemStack(ingredients[i])));
            }
            return new ShapelessRecipeBasic(recipe.getName(), items, recipe);
        } //TODO add this back
         /*else if(type == TYPE_ORE) {
            Object[] items = new Object[ingredients.length];
            for(int i = 0; i < ingredients.length; i++) {
                items[i] = ingredients[i].getInternal();
            }
            //TODO change the string
            return new ShapelessRecipeOre(items, recipe);
        } */else {
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
        if(type == TYPE_BASIC) {
//            ItemStack[] basicIngredients = new ItemStack[recipe.getHeight() * recipe.getWidth()];
            NonNullList<Ingredient> basicIngredients = NonNullList.withSize(recipe.getHeight() * recipe.getWidth(), Ingredient.EMPTY);
            for(int i = 0; i < ingredients.length; i++) {
                basicIngredients.set(posx[i] + posy[i] * recipe.getWidth(), Ingredient.fromStacks(getItemStack(ingredients[i])));
            }

            return new ShapedRecipeBasic(recipe.getName(), basicIngredients, recipe);
        } else if(type == TYPE_ORE) {
            Object[] converted = new Object[recipe.getHeight() * recipe.getWidth()];
            for(int i = 0; i < ingredients.length; i++) {
                converted[posx[i] + posy[i] * recipe.getWidth()] = ingredients[i].getInternal();
            }

            // arguments contents:
            // 1) recipe patterns
            // 2) characters + ingredients

            int counter = 0;
            String[] parts = new String[recipe.getHeight()];
            ArrayList rarguments = new ArrayList();
            for(int i = 0; i < recipe.getHeight(); i++) {
                char[] pattern = new char[recipe.getWidth()];
                for(int j = 0; j < recipe.getWidth(); j++) {
                    int off = i * recipe.getWidth() + j;
                    if(converted[off] == null) {
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
            return new ShapedRecipeOre( new ResourceLocation("crafttweaker", recipe.getName()),rarguments.toArray(), recipe);
        } else {
            return new ShapedRecipeAdvanced(recipe);
        }
    }

    public static ICraftingRecipe toCraftingRecipe(IRecipe recipe) {
        IItemStack output = MineTweakerMC.getIItemStack(recipe.getRecipeOutput());

        if(recipe instanceof ShapelessRecipes) {
            ShapelessRecipes shapeless = (ShapelessRecipes) recipe;

            IIngredient[] ingredients = new IIngredient[shapeless.recipeItems.size()];
            for(int i = 0; i < ingredients.length; i++) {
                ingredients[i] = MineTweakerMC.getIIngredient(shapeless.recipeItems.get(i));
            }

            return new ShapelessRecipe(recipe.getRegistryName().getResourcePath(), output, ingredients, null, null);
        } else if(recipe instanceof ShapedRecipes) {
            ShapedRecipes shaped = (ShapedRecipes) recipe;

            IIngredient[][] ingredients = new IIngredient[shaped.recipeHeight][shaped.recipeWidth];
            for(int i = 0; i < shaped.recipeHeight; i++) {
                for(int j = 0; j < shaped.recipeWidth; j++) {
                    //TODO mess with the ingredient thing at the end here
                    ingredients[i][j] = MineTweakerMC.getIItemStack(shaped.recipeItems.get(i * shaped.recipeWidth + j).getMatchingStacks()[0]);
                }
            }

            return new ShapedRecipe(recipe.getRegistryName().getResourcePath(),output, ingredients, null, null, false);
        } else if(recipe instanceof ShapedOreRecipe) {
            ShapedOreRecipe shaped = (ShapedOreRecipe) recipe;

            int width = ((ShapedOreRecipe) recipe).getWidth();
            int height = ((ShapedOreRecipe) recipe).getHeight();

            IIngredient[][] recipeIngredients = new IIngredient[height][width];
            for(int i = 0; i < height; i++) {
                for(int j = 0; j < width; j++) {
                    //TODO mess with the ingredient thing at the end here
                    recipeIngredients[i][j] = MineTweakerMC.getIIngredient(shaped.getIngredients().get(i * width + j));
                }
            }

            return new ShapedRecipe(recipe.getRegistryName().getResourcePath(),output, recipeIngredients, null, null, false);
        } else if(recipe instanceof ShapelessOreRecipe) {
            ShapelessOreRecipe shapeless = (ShapelessOreRecipe) recipe;

            IIngredient[] ingredients = new IIngredient[shapeless.getIngredients().size()];
            for(int i = 0; i < ingredients.length; i++) {
                ingredients[i] = MineTweakerMC.getIIngredient(shapeless.getIngredients().get(i));
            }

            return new ShapelessRecipe(recipe.getRegistryName().getResourcePath(),output, ingredients, null, null);
        } else {
            return new UnknownRecipe(output);
        }
    }
}
