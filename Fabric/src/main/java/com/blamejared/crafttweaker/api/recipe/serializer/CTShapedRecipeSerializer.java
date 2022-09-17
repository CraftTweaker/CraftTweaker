package com.blamejared.crafttweaker.api.recipe.serializer;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.MirrorAxis;
import com.blamejared.crafttweaker.api.recipe.fun.RecipeFunction2D;
import com.blamejared.crafttweaker.api.recipe.type.CTShapedRecipeBase;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

public class CTShapedRecipeSerializer implements ICTShapedRecipeBaseSerializer {
    
    public static final CTShapedRecipeSerializer INSTANCE = new CTShapedRecipeSerializer();
    
    public CTShapedRecipeSerializer() {
    
    }
    
    @Override
    public CTShapedRecipeBase makeRecipe(ResourceLocation recipeId, IItemStack output, IIngredient[][] ingredients, MirrorAxis mirrorAxis, @Nullable RecipeFunction2D function) {
        
        return new CTShapedRecipeBase(recipeId.getPath(), output, ingredients, mirrorAxis, function);
    }
    
}