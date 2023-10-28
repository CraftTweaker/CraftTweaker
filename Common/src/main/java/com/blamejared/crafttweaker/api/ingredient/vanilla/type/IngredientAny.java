package com.blamejared.crafttweaker.api.ingredient.vanilla.type;

import com.blamejared.crafttweaker.api.ingredient.vanilla.serializer.IngredientAnySerializer;
import com.blamejared.crafttweaker.platform.Services;
import com.google.common.base.Suppliers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class IngredientAny implements CraftTweakerVanillaIngredient {
    
    private static final IngredientAny INSTANCE = new IngredientAny();
    
    public static IngredientAny of() {
        
        return INSTANCE;
    }
    
    public static Ingredient ingredient() {
        
        return Services.PLATFORM.getIngredientAny();
    }
    
    private static final Supplier<List<ItemStack>> MATCHING_STACKS = Suppliers.memoize(() -> BuiltInRegistries.ITEM
            .stream()
            .map(Item::getDefaultInstance)
            .filter(Predicate.not(ItemStack::isEmpty)).toList());
    
    private IngredientAny() {}
    
    @Override
    public boolean test(@Nullable ItemStack stack) {
        
        return stack != null && !stack.isEmpty();
    }
    
    @Override
    public List<ItemStack> getMatchingStacks() {
        
        return MATCHING_STACKS.get();
    }
    
    @Override
    public boolean requiresTesting() {
        
        return true;
    }
    
    @Override
    public boolean isEmpty() {
        
        return false;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public IngredientAnySerializer serializer() {
        
        return IngredientAnySerializer.INSTANCE;
    }
    
}
