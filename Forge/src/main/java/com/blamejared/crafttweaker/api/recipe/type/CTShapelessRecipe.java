package com.blamejared.crafttweaker.api.recipe.type;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.func.RecipeFunction1D;

import javax.annotation.Nullable;

public class CTShapelessRecipe extends CTShapelessRecipeBase {
    
    public CTShapelessRecipe(String name, IItemStack output, IIngredient[] ingredients, @Nullable RecipeFunction1D function) {
        
        super(name, output, ingredients, function);
    }
    
}
