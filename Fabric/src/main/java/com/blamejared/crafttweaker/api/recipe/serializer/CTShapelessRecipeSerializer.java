package com.blamejared.crafttweaker.api.recipe.serializer;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.fun.RecipeFunction1D;
import com.blamejared.crafttweaker.api.recipe.type.CTShapelessRecipeBase;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

public class CTShapelessRecipeSerializer implements ICTShapelessRecipeBaseSerializer {
    
    public static final CTShapelessRecipeSerializer INSTANCE = new CTShapelessRecipeSerializer();
    
    public CTShapelessRecipeSerializer() {
    
    }
    
    @Override
    public CTShapelessRecipeBase makeRecipe(ResourceLocation recipeId, IItemStack output, IIngredient[] ingredients, @Nullable RecipeFunction1D function) {
        
        return new CTShapelessRecipeBase(recipeId.getPath(), output, ingredients, function);
    }
    
}