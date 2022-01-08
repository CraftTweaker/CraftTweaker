package com.blamejared.crafttweaker.api.ingredient.type;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.condition.IIngredientCondition;
import com.blamejared.crafttweaker.api.ingredient.serializer.IngredientConditionedSerializer;
import com.blamejared.crafttweaker.api.item.MCItemStackMutable;
import com.faux.ingredientextension.api.ingredient.serializer.IIngredientSerializer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nullable;

public class IngredientConditioned<I extends IIngredient, T extends IIngredientConditioned<I>> extends IngredientCraftTweaker<T> {
    
    public IngredientConditioned(T crtIngredient) {
        
        super(crtIngredient);
    }
    
    @Override
    public boolean test(@Nullable ItemStack stack) {
        
        return stack != null && getCrTIngredient().matches(new MCItemStackMutable(stack), true);
    }
    
    
    @Override
    public IIngredientSerializer<? extends Ingredient> getSerializer() {
        
        return IngredientConditionedSerializer.INSTANCE;
    }
    
    public IIngredientCondition<I> getCondition() {
        
        return getCrTIngredient().getCondition();
    }
    
}
