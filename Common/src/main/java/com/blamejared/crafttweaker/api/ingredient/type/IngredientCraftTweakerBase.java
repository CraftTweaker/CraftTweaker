package com.blamejared.crafttweaker.api.ingredient.type;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.mixin.common.access.item.AccessIngredient;
import com.blamejared.crafttweaker.platform.Services;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface IngredientCraftTweakerBase extends Predicate<ItemStack> {
    
    IIngredient getCrTIngredient();
    
    default boolean test(@Nullable ItemStack stack) {
        
        return stack != null && getCrTIngredient().matches(Services.PLATFORM.createMCItemStack(stack));
    }
    
    default boolean isSimple() {
        
        return false;
    }
    
    // This is a horrible, horrible system but it is required to solve an issue where dissolving the ingredient before tags have been written to the registry causes that ingredient to be empty.
    static Stream<Ingredient.Value> getValues(IIngredient crtIngredient) {
        
        if(crtIngredient instanceof WrappingIIngredient wrapping) {
            return Arrays.stream(((AccessIngredient) (Object) wrapping.asVanillaIngredient()).crafttweaker$getValues());
        } else if(crtIngredient instanceof IIngredientTransformed<?> transformed) {
            return getValues(transformed.getBaseIngredient());
        } else if(crtIngredient instanceof IIngredientConditioned<?> conditioned) {
            return getValues(conditioned.getBaseIngredient());
        }
        return getValues(crtIngredient.getItems());
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
