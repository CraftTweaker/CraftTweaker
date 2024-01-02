package com.blamejared.crafttweaker.api.ingredient.vanilla.type;

import com.blamejared.crafttweaker.api.ingredient.vanilla.serializer.CraftTweakerVanillaIngredientSerializer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Stream;

public interface CraftTweakerVanillaIngredient {
    
    boolean test(@Nullable ItemStack stack);
    
    List<ItemStack> getMatchingStacks();
    
    boolean requiresTesting();
    
    boolean isEmpty();
    
    <T extends CraftTweakerVanillaIngredient> CraftTweakerVanillaIngredientSerializer<T> serializer();
    
    default Stream<Ingredient.Value> values() {
        
        return getMatchingStacks().stream().map(Ingredient.ItemValue::new);
    }
    
    default boolean singleton() {
        
        return false;
    }
    
}
