package com.blamejared.crafttweaker.api.recipe.serializer;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.type.CTShapelessRecipe;
import com.blamejared.crafttweaker.impl.helper.AccessibleElementsProvider;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.Arrays;


public class CTShapelessRecipeSerializer implements RecipeSerializer<CTShapelessRecipe> {
    
    public static final CTShapelessRecipeSerializer INSTANCE = new CTShapelessRecipeSerializer();
    private static final Codec<CTShapelessRecipe> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    IItemStack.CODEC.fieldOf("output").forGetter(CTShapelessRecipe::getCtOutput),
                    IIngredient.CODEC.listOf()
                            .fieldOf("ingredients")
                            .xmap(iIngredients -> iIngredients.toArray(IIngredient[]::new), Arrays::asList)
                            .forGetter(CTShapelessRecipe::getCtIngredients)
            ).apply(instance, CTShapelessRecipe::new)
    
    );
    
    private CTShapelessRecipeSerializer() {}
    
    @Override
    public Codec<CTShapelessRecipe> codec() {
        
        return CODEC;
    }
    
    @Override
    public CTShapelessRecipe fromNetwork(FriendlyByteBuf buffer) {
        
        int i = buffer.readVarInt();
        IIngredient[] ingredients = new IIngredient[i];
        
        for(int j = 0; j < ingredients.length; ++j) {
            ingredients[j] = IIngredient.fromIngredient(Ingredient.fromNetwork(buffer));
        }
        
        ItemStack output = buffer.readItem();
        return new CTShapelessRecipe(IItemStack.of(output), ingredients);
    }
    
    @Override
    public void toNetwork(FriendlyByteBuf buffer, CTShapelessRecipe recipe) {
        
        
        buffer.writeVarInt(recipe.getIngredients().size());
        for(Ingredient ingredient : recipe.getIngredients()) {
            ingredient.toNetwork(buffer);
        }
        buffer.writeItem(AccessibleElementsProvider.get().registryAccess(recipe::getResultItem));
    }
    
}