package com.blamejared.crafttweaker.api.recipe.serializer;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.MirrorAxis;
import com.blamejared.crafttweaker.api.recipe.fun.RecipeFunction2D;
import com.blamejared.crafttweaker.api.recipe.type.CTShapedRecipe;
import com.blamejared.crafttweaker.impl.helper.AccessibleElementsProvider;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;

import javax.annotation.Nullable;
import java.util.Arrays;

public class CTShapedRecipeSerializer implements RecipeSerializer<CTShapedRecipe> {
    
    public static final CTShapedRecipeSerializer INSTANCE = new CTShapedRecipeSerializer();
    
    public static final Codec<CTShapedRecipe> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            IItemStack.CODEC.fieldOf("output").forGetter(CTShapedRecipe::getCtOutput),
            IIngredient.CODEC.listOf().listOf().fieldOf("ingredients")
                    .xmap(lists -> lists.stream()
                                    .map(ingredients -> ingredients.toArray(IIngredient[]::new))
                                    .toArray(IIngredient[][]::new),
                            ingredients -> Arrays.stream(ingredients)
                                    .map(iIngredients -> Arrays.stream(iIngredients).toList())
                                    .toList())
                    .forGetter(CTShapedRecipe::getCtIngredients), MirrorAxis.CODEC.fieldOf("mirror_axis")
                    .forGetter(CTShapedRecipe::getMirrorAxis)).apply(instance, CTShapedRecipe::new));
    
    private CTShapedRecipeSerializer() {}
    
    @Override
    public Codec<CTShapedRecipe> codec() {
        
        return CODEC;
    }
    
    @Override
    public CTShapedRecipe fromNetwork(FriendlyByteBuf buffer) {
        
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
        return makeRecipe(IItemStack.of(output), inputs, mirrorAxis, null);
    }
    
    @Override
    public void toNetwork(FriendlyByteBuf buffer, CTShapedRecipe recipe) {
        
        buffer.writeVarInt(recipe.getHeight());
        buffer.writeVarInt(recipe.getWidth());
        
        for(Ingredient ingredient : recipe.getIngredients()) {
            ingredient.toNetwork(buffer);
        }
        
        buffer.writeEnum(recipe.getMirrorAxis());
        
        buffer.writeItem(AccessibleElementsProvider.get().registryAccess(recipe::getResultItem));
    }
    
    private static CTShapedRecipe makeRecipe(IItemStack output, IIngredient[][] ingredients, MirrorAxis mirrorAxis, @Nullable RecipeFunction2D function) {
        
        return new CTShapedRecipe(output, ingredients, mirrorAxis, function);
    }
    
}