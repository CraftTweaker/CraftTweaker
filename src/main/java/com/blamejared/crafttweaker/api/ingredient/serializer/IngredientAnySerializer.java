package com.blamejared.crafttweaker.api.ingredient.serializer;

import com.blamejared.crafttweaker.api.item.IngredientAny;
import com.google.gson.JsonObject;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.crafting.IIngredientSerializer;

public enum IngredientAnySerializer implements IIngredientSerializer<IngredientAny> {
    INSTANCE;
    
    @Override
    public IngredientAny parse(PacketBuffer buffer) {
        
        return IngredientAny.INSTANCE;
    }
    
    @Override
    public IngredientAny parse(JsonObject json) {
        
        return IngredientAny.INSTANCE;
    }
    
    @Override
    public void write(PacketBuffer buffer, IngredientAny ingredient) {
        
    }
}