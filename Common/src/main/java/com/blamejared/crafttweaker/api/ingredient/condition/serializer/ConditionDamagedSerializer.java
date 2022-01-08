package com.blamejared.crafttweaker.api.ingredient.condition.serializer;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.ingredient.condition.type.ConditionDamaged;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public enum ConditionDamagedSerializer implements IIngredientConditionSerializer<ConditionDamaged<?>> {
    INSTANCE;
    
    @Override
    public ConditionDamaged<?> fromNetwork(FriendlyByteBuf buffer) {
        
        return new ConditionDamaged<>();
    }
    
    @Override
    public ConditionDamaged<?> fromJson(JsonObject json) {
        
        return new ConditionDamaged<>();
    }
    
    @Override
    public void toNetwork(FriendlyByteBuf buffer, ConditionDamaged<?> ingredient) {
    
    }
    
    @Override
    public JsonObject toJson(ConditionDamaged<?> transformer) {
        
        return new JsonObject();
    }
    
    @Override
    public ResourceLocation getType() {
        
        return new ResourceLocation(CraftTweakerConstants.MOD_ID, "only_damaged");
    }
}