package com.blamejared.crafttweaker.api.ingredient.vanilla.serializer;

import com.blamejared.crafttweaker.CraftTweakerRegistries;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.condition.IIngredientCondition;
import com.blamejared.crafttweaker.api.ingredient.condition.serializer.IIngredientConditionSerializer;
import com.blamejared.crafttweaker.api.ingredient.type.IIngredientConditioned;
import com.blamejared.crafttweaker.api.ingredient.vanilla.type.IngredientConditioned;
import com.mojang.serialization.Codec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class IngredientConditionedSerializer implements CraftTweakerVanillaIngredientSerializer<IngredientConditioned<? extends IIngredient,? extends IIngredientConditioned<?>>> {
    
    public static final IngredientConditionedSerializer INSTANCE = new IngredientConditionedSerializer();
    public static final Codec<IngredientConditioned<? extends IIngredient,? extends IIngredientConditioned<?>>> CODEC = IIngredientConditioned.CODEC.xmap(
            IngredientConditioned::of,
            IngredientConditioned::getCrTIngredient);
    
    private IngredientConditionedSerializer() {}
    
    @Override
    public ResourceLocation getId() {
        
        return IIngredientConditioned.ID;
    }
    
    @Override
    public Codec<IngredientConditioned<?, ?>> codec() {
        
        return CODEC;
    }
    
    @Override
    public IngredientConditioned<? extends IIngredient,? extends IIngredientConditioned<?>> decode(FriendlyByteBuf buffer) {
        
        final IIngredient base = IIngredient.fromIngredient(Ingredient.fromNetwork(buffer));
        final ResourceLocation type = buffer.readResourceLocation();
        final IIngredientConditionSerializer<?> value = CraftTweakerRegistries.REGISTRY_CONDITIONER_SERIALIZER.get(type);
        if(value == null) {
            throw new IllegalArgumentException("Invalid type: " + type);
        }
        
        // noinspection rawtypes,unchecked
        return IngredientConditioned.of(new IIngredientConditioned(base, value.fromNetwork(buffer)));
    }
    
    @Override
    public void encode(FriendlyByteBuf buffer, IngredientConditioned<? extends IIngredient,? extends IIngredientConditioned<?>> ingredient) {
        
        final Ingredient baseIngredient = ingredient.getCrTIngredient().getBaseIngredient().asVanillaIngredient();
        baseIngredient.toNetwork(buffer);
        
        IIngredientCondition<? extends IIngredient> condition = ingredient.getCondition();
        IIngredientConditionSerializer serializer = condition.getSerializer();
        buffer.writeResourceLocation(serializer.getType());
        condition.write(buffer);
    }
    
}
