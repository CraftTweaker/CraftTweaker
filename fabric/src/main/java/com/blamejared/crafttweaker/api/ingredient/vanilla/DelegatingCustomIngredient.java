package com.blamejared.crafttweaker.api.ingredient.vanilla;

import com.blamejared.crafttweaker.api.ingredient.vanilla.type.CraftTweakerVanillaIngredient;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredient;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.minecraft.world.item.ItemStack;

import java.util.List;

record DelegatingCustomIngredient<T extends CraftTweakerVanillaIngredient>(T internal) implements CustomIngredient {
    
    @Override
    public boolean test(ItemStack stack) {
        
        return internal.test(stack);
    }
    
    @Override
    public List<ItemStack> getMatchingStacks() {
        
        return internal.getMatchingStacks();
    }
    
    @Override
    public boolean requiresTesting() {
        
        return internal.requiresTesting();
    }
    
    @Override
    public CustomIngredientSerializer<DelegatingCustomIngredient<T>> getSerializer() {
        
        return CraftTweakerIngredients.Serializers.of(internal.serializer());
    }
    
}
