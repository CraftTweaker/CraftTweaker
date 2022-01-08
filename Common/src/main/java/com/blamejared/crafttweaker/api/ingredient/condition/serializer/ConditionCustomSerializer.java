package com.blamejared.crafttweaker.api.ingredient.condition.serializer;


import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.ingredient.condition.type.ConditionCustom;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public enum ConditionCustomSerializer implements IIngredientConditionSerializer<ConditionCustom<?>> {
    INSTANCE;
    
    @Override
    public ConditionCustom<?> fromNetwork(FriendlyByteBuf buffer) {
        
        return new ConditionCustom<>(buffer.readUtf(), null);
    }
    
    @Override
    public ConditionCustom<?> fromJson(JsonObject json) {
        
        final String uid = json.getAsJsonPrimitive("uid").getAsString();
        return new ConditionCustom<>(uid, null);
    }
    
    @Override
    public void toNetwork(FriendlyByteBuf buffer, ConditionCustom<?> ingredient) {
        
        buffer.writeUtf(ingredient.getUid());
    }
    
    @Override
    public JsonObject toJson(ConditionCustom<?> transformer) {
        
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("uid", transformer.getUid());
        return jsonObject;
    }
    
    @Override
    public ResourceLocation getType() {
        
        return new ResourceLocation(CraftTweakerConstants.MOD_ID, "condition_custom");
    }
}