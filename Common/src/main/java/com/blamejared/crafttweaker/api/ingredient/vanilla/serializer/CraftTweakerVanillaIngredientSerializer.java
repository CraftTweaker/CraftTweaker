package com.blamejared.crafttweaker.api.ingredient.vanilla.serializer;

import com.blamejared.crafttweaker.api.ingredient.vanilla.type.CraftTweakerVanillaIngredient;
import com.mojang.serialization.Codec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public interface CraftTweakerVanillaIngredientSerializer<T extends CraftTweakerVanillaIngredient> {
    
    ResourceLocation getId();
    
    Codec<T> codec();
    
    T decode(FriendlyByteBuf buf);
    
    void encode(FriendlyByteBuf buf, T ingredient);
    
}
