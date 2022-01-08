package com.blamejared.crafttweaker.api.ingredient.condition.serializer;


import com.blamejared.crafttweaker.api.ingredient.condition.IIngredientCondition;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public interface IIngredientConditionSerializer<T extends IIngredientCondition<?>> {
    
    T fromNetwork(FriendlyByteBuf buffer);
    
    T fromJson(JsonObject json);
    
    void toNetwork(FriendlyByteBuf buffer, T ingredient);
    
    JsonObject toJson(T transformer);
    
    ResourceLocation getType();
    
}
