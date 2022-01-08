package com.blamejared.crafttweaker.api.ingredient.condition.serializer;


import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.ingredient.condition.type.ConditionAnyDamage;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public enum ConditionAnyDamagedSerializer implements IIngredientConditionSerializer<ConditionAnyDamage<?>> {
    INSTANCE;
    
    @Override
    public ConditionAnyDamage<?> fromNetwork(FriendlyByteBuf buffer) {
        
        return new ConditionAnyDamage<>();
    }
    
    @Override
    public ConditionAnyDamage<?> fromJson(JsonObject json) {
        
        return new ConditionAnyDamage<>();
    }
    
    @Override
    public void toNetwork(FriendlyByteBuf buffer, ConditionAnyDamage<?> ingredient) {
    
    }
    
    @Override
    public JsonObject toJson(ConditionAnyDamage<?> transformer) {
        
        return new JsonObject();
    }
    
    @Override
    public ResourceLocation getType() {
        
        return new ResourceLocation(CraftTweakerConstants.MOD_ID, "any_damage");
    }
}