package com.blamejared.crafttweaker.impl.ingredients.conditions.serializer;

import com.blamejared.crafttweaker.CraftTweaker;
import com.blamejared.crafttweaker.api.item.conditions.IIngredientConditionSerializer;
import com.blamejared.crafttweaker.impl.ingredients.conditions.ConditionDamaged;
import com.google.gson.JsonObject;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public enum ConditionDamagedSerializer implements IIngredientConditionSerializer<ConditionDamaged<?>> {
    INSTANCE;
    
    @Override
    public ConditionDamaged<?> parse(PacketBuffer buffer) {
        
        return new ConditionDamaged<>();
    }
    
    @Override
    public ConditionDamaged<?> parse(JsonObject json) {
        
        return new ConditionDamaged<>();
    }
    
    @Override
    public void write(PacketBuffer buffer, ConditionDamaged<?> ingredient) {
    
    }
    
    @Override
    public JsonObject toJson(ConditionDamaged<?> transformer) {
        
        return new JsonObject();
    }
    
    @Override
    public ResourceLocation getType() {
        
        return new ResourceLocation(CraftTweaker.MODID, "only_damaged");
    }
}