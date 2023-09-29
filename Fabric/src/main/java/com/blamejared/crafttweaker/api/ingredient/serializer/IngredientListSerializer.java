package com.blamejared.crafttweaker.api.ingredient.serializer;

import com.blamejared.crafttweaker.api.ingredient.type.IIngredientList;
import com.blamejared.crafttweaker.api.ingredient.type.IngredientList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IngredientListSerializer implements CustomIngredientSerializer<IngredientList> {
    
    public static final IngredientListSerializer INSTANCE = new IngredientListSerializer();
    public static final Codec<IngredientList> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(IngredientList::getChildren))
            .apply(instance, IngredientList::new));
    
    private IngredientListSerializer() {}
    
    @Override
    public Codec<IngredientList> getCodec(boolean allowEmpty) {
        
        return CODEC;
    }
    
    @Override
    public IngredientList read(FriendlyByteBuf buffer) {
        
        return new IngredientList(Stream.generate(() -> Ingredient.fromNetwork(buffer))
                .limit(buffer.readVarInt())
                .collect(Collectors.toList()));
    }
    
    @Override
    public ResourceLocation getIdentifier() {
        
        return IIngredientList.ID;
    }
    
    @Override
    public void write(FriendlyByteBuf buffer, IngredientList ingredient) {
        
        buffer.writeVarInt(ingredient.getChildren().size());
        ingredient.getChildren().forEach(c -> c.toNetwork(buffer));
    }
    
}