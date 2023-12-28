package com.blamejared.crafttweaker.api.ingredient.condition.serializer;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.ingredient.condition.type.ConditionDamaged;
import com.mojang.serialization.Codec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class ConditionDamagedSerializer implements IIngredientConditionSerializer<ConditionDamaged<?>> {
    
    public static final ConditionDamagedSerializer INSTANCE = new ConditionDamagedSerializer();
    
    public static final Codec<ConditionDamaged<?>> CODEC = Codec.unit(ConditionDamaged.INSTANCE_RAW);
    
    private ConditionDamagedSerializer() {}
    
    @Override
    public ConditionDamaged<?> fromNetwork(FriendlyByteBuf buffer) {
        
        return ConditionDamaged.INSTANCE_RAW;
    }
    
    @Override
    public void toNetwork(FriendlyByteBuf buffer, ConditionDamaged<?> ingredient) {
    
    }
    
    @Override
    public Codec<ConditionDamaged<?>> codec() {
        
        return CODEC;
    }
    
    @Override
    public ResourceLocation getType() {
        
        return new ResourceLocation(CraftTweakerConstants.MOD_ID, "only_damaged");
    }
    
}