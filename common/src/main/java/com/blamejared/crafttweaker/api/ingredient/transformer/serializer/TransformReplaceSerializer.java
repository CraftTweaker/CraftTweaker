package com.blamejared.crafttweaker.api.ingredient.transformer.serializer;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.ingredient.transformer.IIngredientTransformerSerializer;
import com.blamejared.crafttweaker.api.ingredient.transformer.type.TransformReplace;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

public class TransformReplaceSerializer implements IIngredientTransformerSerializer<TransformReplace> {
    
    public static final TransformReplaceSerializer INSTANCE = new TransformReplaceSerializer();
    public static final MapCodec<TransformReplace> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            IItemStack.OPTIONAL_CODEC.fieldOf("with").forGetter(TransformReplace::replaceWith)
    ).apply(instance, TransformReplace::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, TransformReplace> STREAM_CODEC = IItemStack.OPTIONAL_STREAM_CODEC.map(TransformReplace::new, TransformReplace::replaceWith);
    
    private TransformReplaceSerializer() {}
    
    @Override
    public MapCodec<TransformReplace> codec() {
        
        return CODEC;
    }
    
    @Override
    public StreamCodec<RegistryFriendlyByteBuf, TransformReplace> streamCodec() {
        
        return STREAM_CODEC;
    }
    
    @Override
    public ResourceLocation getType() {
        
        return CraftTweakerConstants.rl("transform_replace");
    }
    
}