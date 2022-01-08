package com.blamejared.crafttweaker.api.recipe.serializer;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.function.RecipeFunctionArray;
import com.blamejared.crafttweaker.api.recipe.type.CTShapelessRecipe;
import com.blamejared.crafttweaker.api.recipe.type.CTShapelessRecipeBase;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistryEntry;
import javax.annotation.Nullable;

public class CTShapelessRecipeSerializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements ICTShapelessRecipeBaseSerializer {
    
    public static final CTShapelessRecipeSerializer INSTANCE = new CTShapelessRecipeSerializer();
    
    public CTShapelessRecipeSerializer() {
        
        setRegistryName(CraftTweakerConstants.rl("shapeless"));
    }
    
    
    @Override
    public CTShapelessRecipeBase makeRecipe(ResourceLocation recipeId, IItemStack output, IIngredient[] ingredients, @Nullable RecipeFunctionArray function) {
        
        return new CTShapelessRecipe(recipeId.getPath(), output, ingredients, function);
    }
    
}