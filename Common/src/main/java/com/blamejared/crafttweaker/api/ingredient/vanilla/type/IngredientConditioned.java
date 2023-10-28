package com.blamejared.crafttweaker.api.ingredient.vanilla.type;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.condition.IIngredientCondition;
import com.blamejared.crafttweaker.api.ingredient.type.IIngredientConditioned;
import com.blamejared.crafttweaker.api.ingredient.vanilla.serializer.IngredientConditionedSerializer;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.platform.Services;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nullable;

public class IngredientConditioned<I extends IIngredient, T extends IIngredientConditioned<I>> extends IngredientCraftTweaker<T> {
    
    public static <I extends IIngredient, T extends IIngredientConditioned<I>> IngredientConditioned<I, T> of(T crtIngredient) {
        
        return new IngredientConditioned<>(crtIngredient);
    }
    
    public static <I extends IIngredient, T extends IIngredientConditioned<I>> Ingredient ingredient(T crtIngredient) {
        
        return Services.PLATFORM.getIngredientConditioned(crtIngredient);
    }
    
    private IngredientConditioned(T crtIngredient) {
        
        super(crtIngredient);
    }
    
    @Override
    public boolean test(@Nullable ItemStack stack) {
        
        return stack != null && getCrTIngredient().matches(IItemStack.of(stack), true);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public IngredientConditionedSerializer serializer() {
        
        return IngredientConditionedSerializer.INSTANCE;
    }
    
    public IIngredientCondition<I> getCondition() {
        
        return getCrTIngredient().getCondition();
    }
    
}
