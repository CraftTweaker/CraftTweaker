package com.blamejared.crafttweaker.api.ingredient.type;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.platform.Services;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public interface IngredientCraftTweakerBase extends Predicate<ItemStack> {
    
    IIngredient getCrTIngredient();
    
    default boolean test(@Nullable ItemStack stack) {
        
        return stack != null && getCrTIngredient().matches(Services.PLATFORM.createMCItemStack(stack));
    }
    
    default boolean isSimple() {
        
        return false;
    }
    
}
