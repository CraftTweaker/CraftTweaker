package com.blamejared.crafttweaker.api.ingredient.condition.serializer;


import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.ingredient.condition.type.ConditionDamagedAtMost;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public enum ConditionDamagedAtMostSerializer implements IIngredientConditionSerializer<ConditionDamagedAtMost<?>> {
    INSTANCE;
    
    @Override
    public ConditionDamagedAtMost<?> fromNetwork(FriendlyByteBuf buffer) {
        
        return new ConditionDamagedAtMost<>(buffer.readVarInt());
    }
    
    @Override
    public ConditionDamagedAtMost<?> fromJson(JsonObject json) {
        
        return new ConditionDamagedAtMost<>(json.getAsJsonPrimitive("damage").getAsInt());
    }
    
    @Override
    public void toNetwork(FriendlyByteBuf buffer, ConditionDamagedAtMost<?> ingredient) {
        
        buffer.writeVarInt(ingredient.getMaxDamage());
    }
    
    @Override
    public JsonObject toJson(ConditionDamagedAtMost<?> transformer) {
        
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("damage", transformer.getMaxDamage());
        return jsonObject;
    }
    
    @Override
    public ResourceLocation getType() {
        
        return new ResourceLocation(CraftTweakerConstants.MOD_ID, "only_damaged_at_most");
    }
}