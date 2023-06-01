package com.blamejared.crafttweaker.api.ingredient.type;

import com.blamejared.crafttweaker.api.ingredient.serializer.IngredientAnySerializer;
import com.google.common.base.Suppliers;
import net.fabricmc.fabric.api.recipe.v1.ingredient.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.*;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.*;

public class IngredientAny implements CustomIngredient, IngredientSingleton<IIngredientAny> {
    
    public static final IngredientAny INSTANCE = new IngredientAny();
    
    private static final Supplier<List<ItemStack>> matchingStacks = Suppliers.memoize(() -> BuiltInRegistries.ITEM
            .stream()
            .map(Item::getDefaultInstance)
            .filter(Predicate.not(ItemStack::isEmpty)).toList());
    
    protected IngredientAny() {
        
    }
    
    @Override
    public boolean test(@Nullable ItemStack stack) {
        
        return stack != null && !stack.isEmpty();
    }
    
    @Override
    public List<ItemStack> getMatchingStacks() {
        
        return matchingStacks.get();
    }
    
    @Override
    public boolean requiresTesting() {
        
        return true;
    }
    
    @Override
    public CustomIngredientSerializer<IngredientAny> getSerializer() {
        
        return IngredientAnySerializer.INSTANCE;
    }
    
    @Override
    public IIngredientAny getInstance() {
        
        return IIngredientAny.INSTANCE;
    }
    
}
