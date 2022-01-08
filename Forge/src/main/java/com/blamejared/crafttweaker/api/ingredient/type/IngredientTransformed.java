package com.blamejared.crafttweaker.api.ingredient.type;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.serializer.IngredientTransformedSerializer;
import com.blamejared.crafttweaker.api.ingredient.transform.IIngredientTransformer;
import com.blamejared.crafttweaker.platform.Services;
import com.google.gson.JsonElement;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class IngredientTransformed<I extends IIngredient, T extends IIngredientTransformed<I>> extends IngredientCraftTweaker<T> {
    
    public IngredientTransformed(T crtIngredient) {
        
        super(crtIngredient);
    }
    
    @Override
    public boolean test(@Nullable ItemStack stack) {
        
        return stack != null && getCrTIngredient().matches(Services.PLATFORM.createMCItemStackMutable(stack), true);
    }
    
    @Override
    public JsonElement toJson() {
        
        return getSerializer().toJson(this);
    }
    
    @Override
    public IngredientTransformedSerializer getSerializer() {
        
        return IngredientTransformedSerializer.INSTANCE;
    }
    
    public IIngredientTransformer<I> getTransformer() {
        
        return getCrTIngredient().getTransformer();
    }
    
    
}
