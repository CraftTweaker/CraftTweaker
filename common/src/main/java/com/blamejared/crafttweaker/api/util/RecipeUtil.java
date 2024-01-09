package com.blamejared.crafttweaker.api.util;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.type.IIngredientEmpty;
import com.google.common.collect.Sets;
import com.google.gson.JsonSyntaxException;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipePattern;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RecipeUtil {
    
    public static ShapedRecipePattern createPattern(IIngredient[][] ingredients) {
        
        return createPattern(ArrayUtil.flattenToNNL(ingredients, () -> Ingredient.EMPTY, IIngredient::asVanillaIngredient));
    }
    
    public static ShapedRecipePattern createPattern(NonNullList<Ingredient> flatIngredients) {
        
        char start = 'a';
        Map<Character, Ingredient> keys = new HashMap<>();
        List<String> pattern = new LinkedList<>();
        for(Ingredient flatIngredient : flatIngredients) {
            char key = start++;
            keys.put(key, flatIngredient);
            pattern.add(String.valueOf(key));
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
