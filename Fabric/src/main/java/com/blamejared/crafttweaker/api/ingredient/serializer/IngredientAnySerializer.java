package com.blamejared.crafttweaker.api.ingredient.serializer;

import com.blamejared.crafttweaker.api.ingredient.type.IIngredientAny;
import com.blamejared.crafttweaker.api.ingredient.type.IngredientAny;
import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class IngredientAnySerializer implements CustomIngredientSerializer<IngredientAny> {
    public static final IngredientAnySerializer INSTANCE = new IngredientAnySerializer();
    public static final Codec<IngredientAny> CODEC = Codec.unit(IngredientAny.INSTANCE);
    private IngredientAnySerializer() {}
    
    @Override
    public ResourceLocation getIdentifier() {
        
        return IIngredientAny.ID;
    }
    
    @Override
    public Codec<IngredientAny> getCodec(boolean allowEmpty) {
        
        return CODEC;
    }
    
    @Override
    public IngredientAny read(FriendlyByteBuf buf) {
    
        return IngredientAny.INSTANCE;
    }
    
    @Override
    public void write(FriendlyByteBuf buf, IngredientAny ingredient) {
    
    }
}
