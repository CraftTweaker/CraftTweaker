package com.blamejared.crafttweaker.api.ingredient.vanilla.serializer;

import com.blamejared.crafttweaker.api.ingredient.type.IIngredientAny;
import com.blamejared.crafttweaker.api.ingredient.vanilla.type.IngredientAny;
import com.mojang.serialization.Codec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class IngredientAnySerializer implements CraftTweakerVanillaIngredientSerializer<IngredientAny> {
    
    public static final Codec<IngredientAny> CODEC = Codec.unit(IngredientAny.of());
    public static IngredientAnySerializer INSTANCE = new IngredientAnySerializer();
    
    private IngredientAnySerializer() {}
    
    @Override
    public ResourceLocation getId() {
        
        return IIngredientAny.ID;
    }
    
    @Override
    public Codec<IngredientAny> codec() {
        
        return CODEC;
    }
    
    @Override
    public IngredientAny decode(FriendlyByteBuf buf) {
        
        return IngredientAny.of();
    }
    
    @Override
    public void encode(FriendlyByteBuf buf, IngredientAny ingredient) {
    
    }
    
}
