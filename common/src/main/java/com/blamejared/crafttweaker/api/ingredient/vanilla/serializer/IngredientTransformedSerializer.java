package com.blamejared.crafttweaker.api.ingredient.vanilla.serializer;

import com.blamejared.crafttweaker.CraftTweakerRegistries;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.transform.IIngredientTransformer;
import com.blamejared.crafttweaker.api.ingredient.transform.serializer.IIngredientTransformerSerializer;
import com.blamejared.crafttweaker.api.ingredient.type.IIngredientTransformed;
import com.blamejared.crafttweaker.api.ingredient.vanilla.type.IngredientTransformed;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.mojang.serialization.Codec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class IngredientTransformedSerializer implements CraftTweakerVanillaIngredientSerializer<IngredientTransformed<? extends IIngredient, ? extends IIngredientTransformed<?>>> {
    
    public static final IngredientTransformedSerializer INSTANCE = new IngredientTransformedSerializer();
    public static final Codec<IngredientTransformed<? extends IIngredient, ? extends IIngredientTransformed<? extends IIngredient>>> CODEC = IIngredientTransformed.CODEC.xmap(
            IngredientTransformed::of,
            IngredientTransformed::getCrTIngredient);
    
    private IngredientTransformedSerializer() {}
    
    @Override
    public Codec<IngredientTransformed<? extends IIngredient, ? extends IIngredientTransformed<? extends IIngredient>>> codec() {
        
        return CODEC;
    }
    
    @Override
    public IngredientTransformed<? extends IIngredient, ? extends IIngredientTransformed<?>> decode(FriendlyByteBuf buffer) {
        
        final IIngredient base = IIngredient.fromIngredient(Ingredient.fromNetwork(buffer));
        final ResourceLocation type = buffer.readResourceLocation();
        final IIngredientTransformerSerializer<?> value = CraftTweakerRegistries.REGISTRY_TRANSFORMER_SERIALIZER.get(type);
        if(value == null) {
            throw new IllegalArgumentException("Invalid type: " + type);
        }
        
        return IngredientTransformed.of(new IIngredientTransformed<>(base, GenericUtil.uncheck(value.fromNetwork(buffer))));
    }
    
    @Override
    public void encode(FriendlyByteBuf buffer, IngredientTransformed<? extends IIngredient, ? extends IIngredientTransformed<?>> ingredient) {
        
        final Ingredient baseIngredient = ingredient.getCrTIngredient().getBaseIngredient().asVanillaIngredient();
        baseIngredient.toNetwork(buffer);
        
        final IIngredientTransformer<?> transformer = ingredient.getTransformer();
        IIngredientTransformerSerializer<?> serializer = transformer.getSerializer();
        buffer.writeResourceLocation(serializer.getType());
        serializer.toNetwork(buffer, GenericUtil.uncheck(transformer));
    }
    
    @Override
    public ResourceLocation getId() {
        
        return IIngredientTransformed.ID;
    }
    
    
}
