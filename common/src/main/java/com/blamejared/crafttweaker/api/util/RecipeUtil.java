package com.blamejared.crafttweaker.api.util;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.type.IIngredientEmpty;
import com.google.common.collect.Sets;
import com.google.gson.JsonSyntaxException;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipePattern;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RecipeUtil {
    
    public static IIngredient[][] shrink(IIngredient[][] ingredients) {
        
        int top = ingredients.length;
        int left = Integer.MAX_VALUE;
        int right = 0;
        int bottom = 0;
        
        for(int rowI = 0; rowI < ingredients.length; rowI++) {
            IIngredient[] row = ingredients[rowI];
            for(int colI = 0; colI < row.length; colI++) {
                IIngredient ingredient = row[colI];
                if(ingredient != null && !ingredient.isEmpty()) {
                    top = Math.min(top, rowI);
                    bottom = Math.max(bottom, rowI);
                    left = Math.min(left, colI);
                    right = Math.max(right, colI);
                }
            }
        }
        if(left == Integer.MAX_VALUE) {
            return new IIngredient[0][0];
        }
        
        IIngredient[][] output = new IIngredient[bottom - top + 1][right - left + 1];
        
        for(int rowI = 0; rowI < output.length; rowI++) {
            IIngredient[] row = output[rowI];
            for(int colI = 0; colI < row.length; colI++) {
                if(ingredients[top + rowI].length <= left + colI) {
                    output[rowI][colI] = IIngredientEmpty.getInstance();
                } else {
                    output[rowI][colI] = ingredients[top + rowI][left + colI];
                }
            }
        }
        return output;
    }
    
    public static IIngredient[][] inflate(final List<IIngredient> flattened, final int width, final int height) {
        
        final IIngredient[][] inflated = new IIngredient[height][width];
        int ingredientIndex = 0;
        for(int row = 0; row < height; row++) {
            for(int col = 0; col < width; col++, ingredientIndex++) {
                inflated[row][col] = flattened.get(ingredientIndex);
            }
        }
        
        return inflated;
    }
    
    public static List<IIngredient> flatten(final IIngredient[][] ingredients, final int width, final int height) {
        
        final int size;
        final List<IIngredient> flattened = Arrays.asList(new IIngredient[size = width * height]);
        for(int i = 0; i < size; ++i) {
            flattened.set(i, ingredients[i / width][i % width]);
        }
        return flattened;
    }
    
    public static ShapedRecipePattern createPattern(IIngredient[][] ingredients) {
        
        int width = ArrayUtil.getMaxWidth(ingredients);
        int height = ingredients.length;
        
        return createPattern(ArrayUtil.flattenToNNL(width, height, ingredients, () -> Ingredient.EMPTY, IIngredient::asVanillaIngredient), width, height);
    }
    
    public static ShapedRecipePattern createPattern(NonNullList<Ingredient> flatIngredients, int width, int height) {
        
        char start = 'a';
        Map<Character, Ingredient> keys = new HashMap<>();
        List<String> pattern = new LinkedList<>();
        int ingredientIndex = 0;
        for(int row = 0; row < height; row++) {
            StringBuilder rowPattern = new StringBuilder();
            for(int col = 0; col < width; col++) {
                Ingredient ingredient = flatIngredients.get(ingredientIndex++);
                if(ingredient.isEmpty()) {
                    rowPattern.append(" ");
                } else {
                    char key = start++;
                    keys.put(key, ingredient);
                    rowPattern.append(key);
                }
            }
            pattern.add(rowPattern.toString());
        }
        
        return ShapedRecipePattern.of(keys, pattern);
    }
    
    public static IIngredient[][] dissolvePattern(String[] pattern, Map<String, IIngredient> keys, int width, int height) {
        
        // " " is reserved for empty
        keys.put(" ", IIngredientEmpty.INSTANCE);
        IIngredient[][] ingredients = new IIngredient[height][width];
        Set<String> set = Sets.newHashSet(keys.keySet());
        set.remove(" ");
        
        
        for(int row = 0; row < pattern.length; ++row) {
            for(int col = 0; col < pattern[row].length(); ++col) {
                String s = pattern[row].substring(col, col + 1);
                IIngredient ingredient = keys.get(s);
                if(ingredient == null) {
                    throw new IllegalArgumentException("Pattern references symbol '" + s + "' but it is not defined in the key");
                }
                
                set.remove(s);
                ingredients[row][col] = ingredient;
            }
        }
        
        if(!set.isEmpty()) {
            throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + set);
        } else {
            return ingredients;
        }
    }
    
}
