package com.blamejared.crafttweaker.api.ingredient.serializer;

import com.blamejared.crafttweaker.api.ingredient.type.IngredientAny;
import com.faux.ingredientextension.api.ingredient.serializer.IIngredientSerializer;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;

public enum IngredientAnySerializer implements IIngredientSerializer<IngredientAny> {
    INSTANCE;
    
    @Override
    public IngredientAny fromNetwork(FriendlyByteBuf bytebuf) {
        
        return IngredientAny.INSTANCE;
    }
    
    @Override
    public void toJson(JsonObject json, IngredientAny ingredient) {
    
    }
    
    @Override
    public IngredientAny fromJson(JsonObject json) {
        
        return IngredientAny.INSTANCE;
    }
    
    @Override
    public void toNetwork(FriendlyByteBuf bytebuf, IngredientAny ingredient) {
    
    }
    
}
