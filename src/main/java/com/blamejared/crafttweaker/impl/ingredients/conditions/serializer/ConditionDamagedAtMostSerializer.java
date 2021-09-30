package com.blamejared.crafttweaker.impl.ingredients.conditions.serializer;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.api.item.conditions.IIngredientConditionSerializer;
import com.blamejared.crafttweaker.impl.ingredients.conditions.ConditionDamagedAtMost;
import com.google.gson.JsonObject;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public enum ConditionDamagedAtMostSerializer implements IIngredientConditionSerializer<ConditionDamagedAtMost<?>> {
    INSTANCE;
    
    @Override
    public ConditionDamagedAtMost<?> parse(PacketBuffer buffer) {
        
        return new ConditionDamagedAtMost<>(buffer.readVarInt());
    }
    
    @Override
    public ConditionDamagedAtMost<?> parse(JsonObject json) {
        
        return new ConditionDamagedAtMost<>(json.getAsJsonPrimitive("damage").getAsInt());
    }
    
    @Override
    public void write(PacketBuffer buffer, ConditionDamagedAtMost<?> ingredient) {
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
        
        return new ResourceLocation(CraftTweaker.MODID, "only_damaged_at_most");
    }
}