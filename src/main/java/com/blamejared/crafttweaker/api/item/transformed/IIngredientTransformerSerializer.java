package com.blamejared.crafttweaker.api.item.transformed;

import com.google.gson.JsonObject;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public interface IIngredientTransformerSerializer<T extends IIngredientTransformer<?>> {
    
    T parse(PacketBuffer buffer);
    
    T parse(JsonObject json);
    
    void write(PacketBuffer buffer, T ingredient);
    
    JsonObject toJson(T transformer);
    
    ResourceLocation getType();
    
}
