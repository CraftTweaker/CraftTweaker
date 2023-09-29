package com.blamejared.crafttweaker.api.ingredient.condition.serializer;


import com.blamejared.crafttweaker.CraftTweakerRegistries;
import com.blamejared.crafttweaker.api.ingredient.condition.IIngredientCondition;
import com.mojang.serialization.Codec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public interface IIngredientConditionSerializer<T extends IIngredientCondition<?>> {
    
    Codec<IIngredientConditionSerializer<?>> CODEC = CraftTweakerRegistries.REGISTRY_CONDITIONER_SERIALIZER.byNameCodec();
    
    T fromNetwork(FriendlyByteBuf buffer);
    
    
    void toNetwork(FriendlyByteBuf buffer, T ingredient);
    
    Codec<T> codec();
    
    ResourceLocation getType();
    
}
