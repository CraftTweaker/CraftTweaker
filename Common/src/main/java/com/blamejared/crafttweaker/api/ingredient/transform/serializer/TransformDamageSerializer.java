package com.blamejared.crafttweaker.api.ingredient.transform.serializer;


import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.ingredient.transform.type.TransformDamage;
import com.mojang.serialization.Codec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class TransformDamageSerializer implements IIngredientTransformerSerializer<TransformDamage<?>> {
    
    public static final TransformDamageSerializer INSTANCE = new TransformDamageSerializer();
    public static final Codec<TransformDamage<?>> CODEC = Codec.INT.xmap(TransformDamage::new, TransformDamage::amount);
    
    private TransformDamageSerializer() {}
    
    @Override
    public TransformDamage<?> fromNetwork(FriendlyByteBuf buffer) {
        
        return new TransformDamage<>(buffer.readVarInt());
    }
    
    @Override
    public Codec<TransformDamage<?>> codec() {
        
        return CODEC;
    }
    
    @Override
    public void toNetwork(FriendlyByteBuf buffer, TransformDamage<?> ingredient) {
        
        buffer.writeVarInt(ingredient.amount());
    }
    
    @Override
    public ResourceLocation getType() {
        
        return CraftTweakerConstants.rl("transform_damage");
    }
    
    
}