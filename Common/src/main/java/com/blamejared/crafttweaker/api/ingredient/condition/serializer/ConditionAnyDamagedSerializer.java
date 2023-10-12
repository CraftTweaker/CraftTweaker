package com.blamejared.crafttweaker.api.ingredient.condition.serializer;


import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.condition.type.ConditionAnyDamage;
import com.mojang.serialization.Codec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class ConditionAnyDamagedSerializer implements IIngredientConditionSerializer<ConditionAnyDamage<? extends IIngredient>> {
    
    public static final ConditionAnyDamagedSerializer INSTANCE = new ConditionAnyDamagedSerializer();
    
    public static final Codec<ConditionAnyDamage<? extends IIngredient>> CODEC = Codec.unit(ConditionAnyDamage.INSTANCE_RAW);
    
    private ConditionAnyDamagedSerializer() {}
    
    @Override
    public ConditionAnyDamage<?> fromNetwork(FriendlyByteBuf buffer) {
        
        return ConditionAnyDamage.INSTANCE_RAW;
    }
    
    @Override
    public void toNetwork(FriendlyByteBuf buffer, ConditionAnyDamage<?> ingredient) {
    
    }
    
    
    @Override
    public Codec<ConditionAnyDamage<?>> codec() {
        
        return CODEC;
    }
    
    @Override
    public ResourceLocation getType() {
        
        return new ResourceLocation(CraftTweakerConstants.MOD_ID, "any_damage");
    }
    
}