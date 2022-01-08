package com.blamejared.crafttweaker.api.ingredient.condition.serializer;


import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.ingredient.condition.type.ConditionDamagedAtLeast;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public enum ConditionDamagedAtLeastSerializer implements IIngredientConditionSerializer<ConditionDamagedAtLeast<?>> {
    INSTANCE;
    
    @Override
    public ConditionDamagedAtLeast<?> fromNetwork(FriendlyByteBuf buffer) {
        
        return new ConditionDamagedAtLeast<>(buffer.readVarInt());
    }
    
    @Override
    public ConditionDamagedAtLeast<?> fromJson(JsonObject json) {
        
        return new ConditionDamagedAtLeast<>(json.getAsJsonPrimitive("damage").getAsInt());
    }
    
    @Override
    public void toNetwork(FriendlyByteBuf buffer, ConditionDamagedAtLeast<?> ingredient) {
        
        buffer.writeVarInt(ingredient.getMinDamage());
    }
    
    @Override
    public JsonObject toJson(ConditionDamagedAtLeast<?> transformer) {
        
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("damage", transformer.getMinDamage());
        return jsonObject;
    }
    
    @Override
    public ResourceLocation getType() {
        
        return new ResourceLocation(CraftTweakerConstants.MOD_ID, "only_damaged_at_least");
    }
}