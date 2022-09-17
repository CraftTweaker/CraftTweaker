package com.blamejared.crafttweaker.api.recipe.serializer;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.MirrorAxis;
import com.blamejared.crafttweaker.api.recipe.func.RecipeFunction2D;
import com.blamejared.crafttweaker.api.recipe.type.CTShapedRecipeBase;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import javax.annotation.Nullable;

public interface ICTShapedRecipeBaseSerializer extends RecipeSerializer<CTShapedRecipeBase> {
    
    @Override
    default CTShapedRecipeBase fromJson(ResourceLocation resourceLocation, JsonObject jsonObject) {
        
        // People shouldn't be making our recipes from json :eyes:
        return makeRecipe(
                CraftTweakerConstants.rl("invalid_recipe"),
                IItemStack.of(new ItemStack(Items.BARRIER)),
                new IIngredient[][] {{IItemStack.of(new ItemStack(Items.BARRIER))}},
                MirrorAxis.NONE,
                null
        );
    }
    
    @Nullable
    @Override
    default CTShapedRecipeBase fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
        
        int height = buffer.readVarInt();
        int width = buffer.readVarInt();
        IIngredient[][] inputs = new IIngredient[height][width];
        
        for(int h = 0; h < inputs.length; h++) {
            for(int w = 0; w < inputs[h].length; w++) {
                inputs[h][w] = IIngredient.fromIngredient(Ingredient.fromNetwork(buffer));
            }
        }
        
        MirrorAxis mirrorAxis = buffer.readEnum(MirrorAxis.class);
        ItemStack output = buffer.readItem();
        return makeRecipe(recipeId, IItemStack.of(output), inputs, mirrorAxis, null);
    }
    
    @Override
    default void toNetwork(FriendlyByteBuf buffer, CTShapedRecipeBase recipe) {
        
        buffer.writeVarInt(recipe.getRecipeHeight());
        buffer.writeVarInt(recipe.getRecipeWidth());
        
        for(Ingredient ingredient : recipe.getIngredients()) {
            ingredient.toNetwork(buffer);
        }
        
        buffer.writeEnum(recipe.getMirrorAxis());
        
        buffer.writeItem(recipe.getResultItem());
    }
    
    default CTShapedRecipeBase makeRecipe(ResourceLocation recipeId, IItemStack output, IIngredient[][] ingredients, MirrorAxis mirrorAxis, @Nullable RecipeFunction2D function) {
        
        return new CTShapedRecipeBase(recipeId.getPath(), output, ingredients, mirrorAxis, function);
    }
    
}