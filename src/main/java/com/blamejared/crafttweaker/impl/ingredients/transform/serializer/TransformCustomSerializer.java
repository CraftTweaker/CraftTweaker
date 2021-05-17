package com.blamejared.crafttweaker.impl.ingredients.transform.serializer;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.api.item.transformed.IIngredientTransformerSerializer;
import com.blamejared.crafttweaker.impl.ingredients.transform.TransformCustom;
import com.google.gson.JsonObject;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public enum TransformCustomSerializer implements IIngredientTransformerSerializer<TransformCustom<?>> {
    INSTANCE;
    
    @Override
    public TransformCustom<?> parse(PacketBuffer buffer) {
        
        return new TransformCustom<>(buffer.readString(), null);
    }
    
    @Override
    public TransformCustom<?> parse(JsonObject json) {
        
        final String uid = json.getAsJsonPrimitive("uid").getAsString();
        return new TransformCustom<>(uid, null);
    }
    
    @Override
    public void write(PacketBuffer buffer, TransformCustom<?> ingredient) {
        
        buffer.writeString(ingredient.getUid());
    }
    
    @Override
    public JsonObject toJson(TransformCustom<?> transformer) {
        
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("uid", transformer.getUid());
        return jsonObject;
    }
    
    @Override
    public ResourceLocation getType() {
        
        return new ResourceLocation(CraftTweaker.MODID, "transform_custom");
    }
    
}