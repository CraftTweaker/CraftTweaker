package com.blamejared.crafttweaker.api.ingredient.vanilla.serializer;

import com.blamejared.crafttweaker.api.ingredient.type.IIngredientList;
import com.blamejared.crafttweaker.api.ingredient.vanilla.type.IngredientList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;

@ParametersAreNonnullByDefault
public class IngredientListSerializer implements CraftTweakerVanillaIngredientSerializer<IngredientList> {
    
    public static final IngredientListSerializer INSTANCE = new IngredientListSerializer();
    public static final Codec<IngredientList> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(IngredientList::getChildren)
    ).apply(instance, IngredientList::of));
    
    private IngredientListSerializer() {}
    
    @Override
    public ResourceLocation getId() {
        
        return IIngredientList.ID;
    }
    
    @Override
    public Codec<IngredientList> codec() {
        
        return CODEC;
    }
    
    @Override
    public IngredientList decode(FriendlyByteBuf buffer) {
        
        return IngredientList.of(buffer.readCollection(ArrayList::new, Ingredient::fromNetwork));
    }
    
    @Override
    public void encode(FriendlyByteBuf buffer, IngredientList ingredient) {
        
        buffer.writeCollection(ingredient.getChildren(), (buf, ing) -> ing.toNetwork(buf));
    }
    
}
