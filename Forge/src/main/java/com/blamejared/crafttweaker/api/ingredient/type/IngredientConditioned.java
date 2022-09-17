package com.blamejared.crafttweaker.api.ingredient.type;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.condition.IIngredientCondition;
import com.blamejared.crafttweaker.api.ingredient.serializer.IngredientConditionedSerializer;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.google.gson.JsonElement;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class IngredientConditioned<I extends IIngredient, T extends IIngredientConditioned<I>> extends IngredientCraftTweaker<T> {
    
    public IngredientConditioned(T crtIngredient) {
        
        super(crtIngredient);
    }
    
    @Override
    public boolean test(@Nullable ItemStack stack) {
        
        return stack != null && getCrTIngredient().matches(IItemStack.ofMutable(stack), true);
    }
    
    @Override
    public JsonElement toJson() {
        
        return getSerializer().toJson(this);
    }
    
    @Override
    public IngredientConditionedSerializer getSerializer() {
        
        return IngredientConditionedSerializer.INSTANCE;
    }
    
    
    public IIngredientCondition<I> getCondition() {
        
        return getCrTIngredient().getCondition();
    }
    
}
