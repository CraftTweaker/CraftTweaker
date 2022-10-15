package com.blamejared.crafttweaker.api.ingredient.type;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface IngredientCraftTweakerBase extends Predicate<ItemStack> {
    
    IIngredient getCrTIngredient();
    
    default boolean test(@Nullable ItemStack stack) {
        
        return stack != null && getCrTIngredient().matches(IItemStack.of(stack));
    }
    
    default boolean isSimple() {
        
        return false;
    }
    
    static Stream<Ingredient.Value> getValues(IItemStack[] items) {
        
        // TODO This may cause issues since we have such a big value array, it needs more investigation
        return Arrays.stream(items)
                .map(IIngredient::getItems)
                .flatMap(Arrays::stream)
                .map(IItemStack::getInternal)
                .map(Ingredient.ItemValue::new);
    }
    
}
