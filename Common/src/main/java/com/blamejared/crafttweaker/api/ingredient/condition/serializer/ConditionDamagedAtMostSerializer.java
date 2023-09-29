package com.blamejared.crafttweaker.api.ingredient.condition.serializer;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.ingredient.condition.type.ConditionDamagedAtMost;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class ConditionDamagedAtMostSerializer implements IIngredientConditionSerializer<ConditionDamagedAtMost<?>> {
    
    public static final ConditionDamagedAtMostSerializer INSTANCE = new ConditionDamagedAtMostSerializer();
    public static final Codec<ConditionDamagedAtMost<?>> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    Codec.INT.fieldOf("damage").forGetter(ConditionDamagedAtMost::getMaxDamage)).
            apply(instance, ConditionDamagedAtMost::new));
    
    private ConditionDamagedAtMostSerializer() {}
    
    
    @Override
    public Codec<ConditionDamagedAtMost<?>> codec() {
        
        return CODEC;
    }
    
    @Override
    public ConditionDamagedAtMost<?> fromNetwork(FriendlyByteBuf buffer) {
        
        return new ConditionDamagedAtMost<>(buffer.readVarInt());
    }
    
    @Override
    public void toNetwork(FriendlyByteBuf buffer, ConditionDamagedAtMost<?> ingredient) {
        
        buffer.writeVarInt(ingredient.getMaxDamage());
    }
    
    @Override
    public ResourceLocation getType() {
        
        return new ResourceLocation(CraftTweakerConstants.MOD_ID, "only_damaged_at_most");
    }
    
}