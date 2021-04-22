package com.blamejared.crafttweaker.impl.ingredients.conditions.serializer;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.api.item.conditions.IIngredientConditionSerializer;
import com.blamejared.crafttweaker.impl.ingredients.conditions.ConditionAnyDamage;
import com.google.gson.JsonObject;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public enum ConditionAnyDamagedSerializer implements IIngredientConditionSerializer<ConditionAnyDamage<?>> {
    INSTANCE;
    
    @Override
    public ConditionAnyDamage<?> parse(PacketBuffer buffer) {
        
        return new ConditionAnyDamage<>();
    }
    
    @Override
    public ConditionAnyDamage<?> parse(JsonObject json) {
        
        return new ConditionAnyDamage<>();
    }
    
    @Override
    public void write(PacketBuffer buffer, ConditionAnyDamage<?> ingredient) {
    
    }
    
    @Override
    public JsonObject toJson(ConditionAnyDamage<?> transformer) {
        
        return new JsonObject();
    }
    
    @Override
    public ResourceLocation getType() {
        
        return new ResourceLocation(CraftTweaker.MODID, "any_damage");
    }
}