package com.blamejared.crafttweaker.impl.ingredients.transform.serializer;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.api.item.transformed.IIngredientTransformerSerializer;
import com.blamejared.crafttweaker.impl.ingredients.transform.TransformDamage;
import com.google.gson.JsonObject;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public enum TransformDamageSerializer implements IIngredientTransformerSerializer<TransformDamage<?>> {
    INSTANCE;
    
    @Override
    public TransformDamage<?> parse(PacketBuffer buffer) {
        
        return new TransformDamage<>(buffer.readVarInt());
    }
    
    @Override
    public TransformDamage<?> parse(JsonObject json) {
        
        return new TransformDamage<>(json.getAsJsonPrimitive("amount").getAsInt());
    }
    
    @Override
    public void write(PacketBuffer buffer, TransformDamage<?> ingredient) {
        
        buffer.writeVarInt(ingredient.getAmount());
    }
    
    @Override
    public JsonObject toJson(TransformDamage<?> transformer) {
        
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("amount", transformer.getAmount());
        return jsonObject;
    }
    
    @Override
    public ResourceLocation getType() {
        
        return new ResourceLocation(CraftTweaker.MODID, "transform_damage");
    }
    
}