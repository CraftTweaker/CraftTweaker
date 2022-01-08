package com.blamejared.crafttweaker.api.ingredient.serializer;

import com.blamejared.crafttweaker.api.ingredient.type.IngredientAny;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.common.crafting.IIngredientSerializer;

public enum IngredientAnySerializer implements IIngredientSerializer<IngredientAny> {
    INSTANCE;
    
    @Override
    public IngredientAny parse(FriendlyByteBuf bytebuf) {
        
        return IngredientAny.INSTANCE;
    }
    
    @Override
    public IngredientAny parse(JsonObject json) {
        
        return IngredientAny.INSTANCE;
    }
    
    @Override
    public void write(FriendlyByteBuf bytebuf, IngredientAny ingredient) {
    
    }
    
}
