package com.blamejared.crafttweaker.api.ingredient.transform.serializer;


import com.blamejared.crafttweaker.CraftTweakerRegistries;
import com.blamejared.crafttweaker.api.ingredient.transform.IIngredientTransformer;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public interface IIngredientTransformerSerializer<T extends IIngredientTransformer<?>> {
    
    Codec<IIngredientTransformerSerializer<?>> CODEC = CraftTweakerRegistries.REGISTRY_TRANSFORMER_SERIALIZER.byNameCodec();
    
    T fromNetwork(FriendlyByteBuf buffer);
    
    Codec<T> codec();
    
    void toNetwork(FriendlyByteBuf buffer, T ingredient);
    
    ResourceLocation getType();
    
}
