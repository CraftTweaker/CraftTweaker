package com.blamejared.crafttweaker.api.ingredient.vanilla.type;

import com.blamejared.crafttweaker.api.ingredient.vanilla.serializer.CraftTweakerVanillaIngredientSerializer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface CraftTweakerVanillaIngredient {
    
    boolean test(@Nullable ItemStack stack);
    
    List<ItemStack> getMatchingStacks();
    
    boolean requiresTesting();
    
    <T extends CraftTweakerVanillaIngredient> CraftTweakerVanillaIngredientSerializer<T> serializer();
    
    default boolean singleton() {
        
        return false;
    }
    
}
