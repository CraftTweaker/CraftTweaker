package com.blamejared.crafttweaker.api.ingredient.transform.serializer;


import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.ingredient.transform.type.TransformReuse;
import com.mojang.serialization.Codec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class TransformReuseSerializer implements IIngredientTransformerSerializer<TransformReuse<?>> {
    
    public static final TransformReuseSerializer INSTANCE = new TransformReuseSerializer();
    public static final Codec<TransformReuse<?>> CODEC = Codec.unit(TransformReuse.INSTANCE_RAW);
    
    private TransformReuseSerializer() {}
    
    @Override
    public TransformReuse<?> fromNetwork(FriendlyByteBuf buffer) {
        
        return TransformReuse.getInstance();
    }
    
    @Override
    public Codec<TransformReuse<?>> codec() {
        
        return CODEC;
    }
    
    @Override
    public void toNetwork(FriendlyByteBuf buffer, TransformReuse<?> ingredient) {
        //No-OP
    }
    
    @Override
    public ResourceLocation getType() {
        
        return CraftTweakerConstants.rl("transform_reuse");
    }
    
}