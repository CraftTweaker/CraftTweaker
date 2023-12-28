package com.blamejared.crafttweaker.api.ingredient.transform.serializer;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.ingredient.transform.type.TransformReplace;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.mojang.serialization.Codec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class TransformReplaceSerializer implements IIngredientTransformerSerializer<TransformReplace<?>> {
    
    public static final TransformReplaceSerializer INSTANCE = new TransformReplaceSerializer();
    public static final Codec<TransformReplace<?>> CODEC = IItemStack.CODEC.xmap(TransformReplace::new, TransformReplace::replaceWith);
    
    private TransformReplaceSerializer() {}
    
    @Override
    public Codec<TransformReplace<?>> codec() {
        
        return CODEC;
    }
    
    @Override
    public TransformReplace<?> fromNetwork(FriendlyByteBuf buffer) {
        
        final ItemStack replaceWith = buffer.readItem();
        return new TransformReplace<>(IItemStack.of(replaceWith));
    }
    
    @Override
    public void toNetwork(FriendlyByteBuf buffer, TransformReplace<?> ingredient) {
        
        buffer.writeItem(ingredient.replaceWith().getInternal());
    }
    
    @Override
    public ResourceLocation getType() {
        
        return CraftTweakerConstants.rl("transform_replace");
    }
    
}