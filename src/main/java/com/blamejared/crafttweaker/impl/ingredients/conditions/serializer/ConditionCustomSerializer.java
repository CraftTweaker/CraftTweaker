package com.blamejared.crafttweaker.impl.ingredients.conditions.serializer;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.api.item.conditions.IIngredientConditionSerializer;
import com.blamejared.crafttweaker.impl.ingredients.conditions.ConditionCustom;
import com.google.gson.JsonObject;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public enum ConditionCustomSerializer implements IIngredientConditionSerializer<ConditionCustom<?>> {
    INSTANCE;
    
    @Override
    public ConditionCustom<?> parse(PacketBuffer buffer) {
        
        return new ConditionCustom<>(buffer.readString(), null);
    }
    
    @Override
    public ConditionCustom<?> parse(JsonObject json) {
        
        final String uid = json.getAsJsonPrimitive("uid").getAsString();
        return new ConditionCustom<>(uid, null);
    }
    
    @Override
    public void write(PacketBuffer buffer, ConditionCustom<?> ingredient) {
        
        buffer.writeString(ingredient.getUid());
    }
    
    @Override
    public JsonObject toJson(ConditionCustom<?> transformer) {
        
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("uid", transformer.getUid());
        return jsonObject;
    }
    
    @Override
    public ResourceLocation getType() {
        
        return new ResourceLocation(CraftTweaker.MODID, "condition_custom");
    }
}