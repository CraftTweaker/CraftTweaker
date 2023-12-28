package com.blamejared.crafttweaker.api.ingredient.condition.serializer;


import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.ingredient.condition.type.ConditionCustom;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class ConditionCustomSerializer implements IIngredientConditionSerializer<ConditionCustom<?>> {
    
    public static final ConditionCustomSerializer INSTANCE = new ConditionCustomSerializer();
    public static final Codec<ConditionCustom<?>> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("uid").forGetter(ConditionCustom::getUid)
    ).apply(instance, s -> new ConditionCustom<>(s, null)));
    
    private ConditionCustomSerializer() {}
    
    @Override
    public ConditionCustom<?> fromNetwork(FriendlyByteBuf buffer) {
        
        return new ConditionCustom<>(buffer.readUtf(), null);
    }
    
    @Override
    public void toNetwork(FriendlyByteBuf buffer, ConditionCustom<?> ingredient) {
        
        buffer.writeUtf(ingredient.getUid());
    }
    
    @Override
    public Codec<ConditionCustom<?>> codec() {
        
        return CODEC;
    }
    
    @Override
    public ResourceLocation getType() {
        
        return new ResourceLocation(CraftTweakerConstants.MOD_ID, "condition_custom");
    }
    
}