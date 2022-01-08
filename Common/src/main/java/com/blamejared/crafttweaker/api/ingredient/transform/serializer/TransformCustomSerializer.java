package com.blamejared.crafttweaker.api.ingredient.transform.serializer;


import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.ingredient.transform.type.TransformCustom;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public enum TransformCustomSerializer implements IIngredientTransformerSerializer<TransformCustom<?>> {
    INSTANCE;
    
    @Override
    public TransformCustom<?> fromNetwork(FriendlyByteBuf buffer) {
        
        return new TransformCustom<>(buffer.readUtf(), null);
    }
    
    @Override
    public TransformCustom<?> fromJson(JsonObject json) {
        
        final String uid = json.getAsJsonPrimitive("uid").getAsString();
        return new TransformCustom<>(uid, null);
    }
    
    @Override
    public void toNetwork(FriendlyByteBuf buffer, TransformCustom<?> ingredient) {
        
        buffer.writeUtf(ingredient.getUid());
    }
    
    @Override
    public JsonObject toJson(TransformCustom<?> transformer) {
        
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("uid", transformer.getUid());
        return jsonObject;
    }
    
    @Override
    public ResourceLocation getType() {
        
        return CraftTweakerConstants.rl("transform_custom");
    }
    
}