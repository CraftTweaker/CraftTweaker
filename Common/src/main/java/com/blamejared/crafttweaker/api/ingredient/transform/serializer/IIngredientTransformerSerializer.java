package com.blamejared.crafttweaker.api.ingredient.transform.serializer;


import com.blamejared.crafttweaker.api.ingredient.transform.IIngredientTransformer;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public interface IIngredientTransformerSerializer<T extends IIngredientTransformer<?>> {
    
    T fromNetwork(FriendlyByteBuf buffer);
    
    T fromJson(JsonObject json);
    
    void toNetwork(FriendlyByteBuf buffer, T ingredient);
    
    JsonObject toJson(T transformer);
    
    ResourceLocation getType();
    
}
