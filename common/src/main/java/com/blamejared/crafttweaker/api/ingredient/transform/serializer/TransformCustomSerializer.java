package com.blamejared.crafttweaker.api.ingredient.transform.serializer;


import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.ingredient.transform.type.TransformCustom;
import com.mojang.serialization.Codec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class TransformCustomSerializer implements IIngredientTransformerSerializer<TransformCustom<?>> {
    
    public static final TransformCustomSerializer INSTANCE = new TransformCustomSerializer();
    public static final Codec<TransformCustom<?>> CODEC = Codec.STRING.xmap(TransformCustom::new, TransformCustom::getUid);
    
    private TransformCustomSerializer() {}
    
    @Override
    public Codec<TransformCustom<?>> codec() {
        
        return CODEC;
    }
    
    @Override
    public TransformCustom<?> fromNetwork(FriendlyByteBuf buffer) {
        
        return new TransformCustom<>(buffer.readUtf(), null);
    }
    
    @Override
    public void toNetwork(FriendlyByteBuf buffer, TransformCustom<?> ingredient) {
        
        buffer.writeUtf(ingredient.getUid());
    }
    
    @Override
    public ResourceLocation getType() {
        
        return CraftTweakerConstants.rl("transform_custom");
    }
    
}