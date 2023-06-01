package com.blamejared.crafttweaker.api.ingredient.type;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.serializer.IngredientTransformedSerializer;
import com.blamejared.crafttweaker.api.ingredient.transform.IIngredientTransformer;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;

public class IngredientTransformed<I extends IIngredient, T extends IIngredientTransformed<I>> extends IngredientCraftTweaker<T> {
    
    public IngredientTransformed(T crtIngredient) {
        
        super(crtIngredient);
    }
    
    public IIngredientTransformer<I> getTransformer() {
        
        return getCrTIngredient().getTransformer();
    }
    
    @Override
    public CustomIngredientSerializer<IngredientTransformed<?, ?>> getSerializer() {
        
        return IngredientTransformedSerializer.INSTANCE;
    }
    
}
