package com.blamejared.crafttweaker.api.ingredient.transform.serializer;


import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.ingredient.transform.type.TransformDamage;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public enum TransformDamageSerializer implements IIngredientTransformerSerializer<TransformDamage<?>> {
    INSTANCE;
    
    @Override
    public TransformDamage<?> fromNetwork(FriendlyByteBuf buffer) {
        
        return new TransformDamage<>(buffer.readVarInt());
    }
    
    @Override
    public TransformDamage<?> fromJson(JsonObject json) {
        
        return new TransformDamage<>(json.getAsJsonPrimitive("amount").getAsInt());
    }
    
    @Override
    public void toNetwork(FriendlyByteBuf buffer, TransformDamage<?> ingredient) {
        
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
        
        return CraftTweakerConstants.rl("transform_damage");
    }
    
}