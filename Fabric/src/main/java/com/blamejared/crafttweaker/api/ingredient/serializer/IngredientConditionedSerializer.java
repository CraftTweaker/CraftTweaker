package com.blamejared.crafttweaker.api.ingredient.serializer;

import com.blamejared.crafttweaker.CraftTweakerRegistries;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.condition.IIngredientCondition;
import com.blamejared.crafttweaker.api.ingredient.condition.serializer.IIngredientConditionSerializer;
import com.blamejared.crafttweaker.api.ingredient.type.IIngredientConditioned;
import com.blamejared.crafttweaker.api.ingredient.type.IngredientConditioned;
import com.blamejared.crafttweaker.api.ingredient.type.IngredientCraftTweaker;
import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class IngredientConditionedSerializer implements CustomIngredientSerializer<IngredientConditioned<?, ?>> {
    
    public static final IngredientConditionedSerializer INSTANCE = new IngredientConditionedSerializer();
    public static final Codec<IngredientConditioned<?, ?>> CODEC = IIngredientConditioned.CODEC.xmap(IngredientConditioned::new, IngredientCraftTweaker::getCrTIngredient);
    
    private IngredientConditionedSerializer() {}
    
    @Override
    public ResourceLocation getIdentifier() {
        
        return IIngredientConditioned.ID;
    }
    
    @Override
    public Codec<IngredientConditioned<?, ?>> getCodec(boolean allowEmpty) {
        
        return CODEC;
    }
    
    @Override
    public IngredientConditioned<?, ?> read(FriendlyByteBuf buffer) {
        
        final IIngredient base = IIngredient.fromIngredient(Ingredient.fromNetwork(buffer));
        final ResourceLocation type = buffer.readResourceLocation();
        final IIngredientConditionSerializer<?> value = CraftTweakerRegistries.REGISTRY_CONDITIONER_SERIALIZER.get(type);
        if(value == null) {
            throw new IllegalArgumentException("Invalid type: " + type);
        }
        
        // noinspection rawtypes,unchecked
        return new IngredientConditioned(new IIngredientConditioned(base, value.fromNetwork(buffer)));
    }
    
    @Override
    public void write(FriendlyByteBuf buffer, IngredientConditioned<?, ?> ingredient) {
        
        final Ingredient baseIngredient = ingredient.getCrTIngredient().getBaseIngredient().asVanillaIngredient();
        baseIngredient.toNetwork(buffer);
        
        final IIngredientCondition<?> condition = ingredient.getCondition();
        final IIngredientConditionSerializer<? extends IIngredientCondition<?>> serializer = condition.getSerializer();
        buffer.writeResourceLocation(serializer.getType());
        condition.write(buffer);
    }
    
}
