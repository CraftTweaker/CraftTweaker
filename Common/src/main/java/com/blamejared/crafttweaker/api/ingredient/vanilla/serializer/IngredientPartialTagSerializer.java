package com.blamejared.crafttweaker.api.ingredient.vanilla.serializer;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.ingredient.vanilla.type.IngredientPartialTag;
import com.mojang.serialization.Codec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class IngredientPartialTagSerializer implements CraftTweakerVanillaIngredientSerializer<IngredientPartialTag> {
    
    public static final IngredientPartialTagSerializer INSTANCE = new IngredientPartialTagSerializer();
    public static final Codec<IngredientPartialTag> CODEC = ItemStack.CODEC.xmap(IngredientPartialTag::new, IngredientPartialTag::getStack);
    public static final ResourceLocation ID = CraftTweakerConstants.rl("partial_tag");
    
    private IngredientPartialTagSerializer() {}
    
    @Override
    public Codec<IngredientPartialTag> codec() {
        
        return CODEC;
    }
    
    @Override
    public IngredientPartialTag decode(FriendlyByteBuf buffer) {
        
        return new IngredientPartialTag(buffer.readItem());
    }
    
    @Override
    public void encode(FriendlyByteBuf buffer, IngredientPartialTag ingredient) {
        
        buffer.writeItem(ingredient.getStack());
    }
    
    @Override
    public ResourceLocation getId() {
        
        return IngredientPartialTagSerializer.ID;
    }
    
}
