package com.blamejared.crafttweaker.impl.ingredients.conditions.serializer;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.api.item.conditions.IIngredientConditionSerializer;
import com.blamejared.crafttweaker.impl.ingredients.conditions.ConditionDamagedAtLeast;
import com.google.gson.JsonObject;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public enum ConditionDamagedAtLeastSerializer implements IIngredientConditionSerializer<ConditionDamagedAtLeast<?>> {
    INSTANCE;
    
    @Override
    public ConditionDamagedAtLeast<?> parse(PacketBuffer buffer) {
        
        return new ConditionDamagedAtLeast<>(buffer.readVarInt());
    }
    
    @Override
    public ConditionDamagedAtLeast<?> parse(JsonObject json) {
        
        return new ConditionDamagedAtLeast<>(json.getAsJsonPrimitive("damage").getAsInt());
    }
    
    @Override
    public void write(PacketBuffer buffer, ConditionDamagedAtLeast<?> ingredient) {
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
        
        return new ResourceLocation(CraftTweaker.MODID, "only_damaged_at_least");
    }
}