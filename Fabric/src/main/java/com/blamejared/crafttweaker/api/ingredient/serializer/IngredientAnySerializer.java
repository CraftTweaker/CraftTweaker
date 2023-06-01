package com.blamejared.crafttweaker.api.ingredient.serializer;

import com.blamejared.crafttweaker.api.ingredient.type.IIngredientAny;
import com.blamejared.crafttweaker.api.ingredient.type.IngredientAny;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public enum IngredientAnySerializer implements CustomIngredientSerializer<IngredientAny> {
    INSTANCE;
    
    @Override
    public ResourceLocation getIdentifier() {
        
        return IIngredientAny.ID;
    }
    
    @Override
    public IngredientAny read(JsonObject json) {
    
        return IngredientAny.INSTANCE;
    }
    
    @Override
    public void write(JsonObject json, IngredientAny ingredient) {
    
    }
    
    @Override
    public IngredientAny read(FriendlyByteBuf buf) {
    
        return IngredientAny.INSTANCE;
    }
    
    @Override
    public void write(FriendlyByteBuf buf, IngredientAny ingredient) {
    
    }
}
