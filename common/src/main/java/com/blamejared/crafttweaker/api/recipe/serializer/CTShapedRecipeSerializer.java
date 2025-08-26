package com.blamejared.crafttweaker.api.recipe.serializer;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.MirrorAxis;
import com.blamejared.crafttweaker.api.recipe.fun.RecipeFunction2D;
import com.blamejared.crafttweaker.api.recipe.type.CTShapedRecipe;
import com.blamejared.crafttweaker.api.util.RecipeUtil;
import com.blamejared.crafttweaker.impl.helper.AccessibleElementsProvider;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;

import javax.annotation.Nullable;
import java.util.Arrays;

public class CTShapedRecipeSerializer implements RecipeSerializer<CTShapedRecipe> {
    
    public static final CTShapedRecipeSerializer INSTANCE = new CTShapedRecipeSerializer();
    
    public static final MapCodec<CTShapedRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
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
    
    public static final StreamCodec<RegistryFriendlyByteBuf, CTShapedRecipe> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            CTShapedRecipe::getHeight,
            ByteBufCodecs.VAR_INT,
            CTShapedRecipe::getWidth,
            IIngredient.STREAM_CODEC.apply(ByteBufCodecs.list()),
            CTShapedRecipe::getFlatCtIngredients,
            IItemStack.STREAM_CODEC,
            CTShapedRecipe::getCtOutput, // TODO 1.21 when mojang eventually makes an enum stream codec, use it.
            new StreamCodec<>() {
                @Override
                public MirrorAxis decode(RegistryFriendlyByteBuf buffer) {
                    
                    return buffer.readEnum(MirrorAxis.class);
                }
                
                @Override
                public void encode(RegistryFriendlyByteBuf buffer, MirrorAxis axis) {
                    
                    buffer.writeEnum(axis);
                }
            },
            CTShapedRecipe::getMirrorAxis,
            (height, width, ingredients, output, mirrorAxis) -> new CTShapedRecipe(output, RecipeUtil.inflate(ingredients, width, height), mirrorAxis)
    );
    
    
    private CTShapedRecipeSerializer() {}
    
    @Override
    public MapCodec<CTShapedRecipe> codec() {
        
        return CODEC;
    }
    
    @Override
    public StreamCodec<RegistryFriendlyByteBuf, CTShapedRecipe> streamCodec() {
        
        return STREAM_CODEC;
    }
    
    private static CTShapedRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
        
        int height = buffer.readVarInt();
        int width = buffer.readVarInt();
        IIngredient[][] inputs = new IIngredient[height][width];
        
        for(int h = 0; h < inputs.length; h++) {
            for(int w = 0; w < inputs[h].length; w++) {
                inputs[h][w] = IIngredient.fromIngredient(Ingredient.CONTENTS_STREAM_CODEC.decode(buffer));
            }
        }
        
        MirrorAxis mirrorAxis = buffer.readEnum(MirrorAxis.class);
        ItemStack output = ItemStack.STREAM_CODEC.decode(buffer);
        return makeRecipe(IItemStack.of(output), inputs, mirrorAxis, null);
    }
    
    private static void toNetwork(RegistryFriendlyByteBuf buffer, CTShapedRecipe recipe) {
        
        buffer.writeVarInt(recipe.getHeight());
        buffer.writeVarInt(recipe.getWidth());
        
        for(Ingredient ingredient : recipe.getIngredients()) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, ingredient);
        }
        
        buffer.writeEnum(recipe.getMirrorAxis());
        ItemStack.STREAM_CODEC.encode(buffer, AccessibleElementsProvider.get().registryAccess(recipe::getResultItem));
    }
    
    private static CTShapedRecipe makeRecipe(IItemStack output, IIngredient[][] ingredients, MirrorAxis mirrorAxis, @Nullable RecipeFunction2D function) {
        
        return new CTShapedRecipe(output, ingredients, mirrorAxis, function);
    }
    
}