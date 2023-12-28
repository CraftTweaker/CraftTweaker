package com.blamejared.crafttweaker.api.ingredient.vanilla;

import com.blamejared.crafttweaker.api.ingredient.vanilla.serializer.CraftTweakerVanillaIngredientSerializer;
import com.blamejared.crafttweaker.api.ingredient.vanilla.type.CraftTweakerVanillaIngredient;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

record DelegatingCustomIngredientSerializer<T extends CraftTweakerVanillaIngredient>(
        CraftTweakerVanillaIngredientSerializer<T> internal) implements CustomIngredientSerializer<DelegatingCustomIngredient<T>> {
    
    private static final Map<Codec<? extends CraftTweakerVanillaIngredient>, Codec<? extends DelegatingCustomIngredient<?>>> CODEC_CACHE = new HashMap<>();
    
    @Override
    public ResourceLocation getIdentifier() {
        
        return internal.getId();
    }
    
    @Override
    public Codec<DelegatingCustomIngredient<T>> getCodec(boolean allowEmpty) {
        
        return GenericUtil.uncheck(CODEC_CACHE.computeIfAbsent(internal.codec(), codec -> codec.<DelegatingCustomIngredient<?>> xmap(CraftTweakerIngredients.Ingredients::of, GenericUtil.uncheckFunc(DelegatingCustomIngredient::internal))));
    }
    
    @Override
    public DelegatingCustomIngredient<T> read(FriendlyByteBuf buf) {
        
        return CraftTweakerIngredients.Ingredients.of(this.internal.decode(buf));
    }
    
    @Override
    public void write(FriendlyByteBuf buf, DelegatingCustomIngredient<T> ingredient) {
        
        ingredient.internal().serializer().encode(buf, ingredient.internal());
    }
    
}
