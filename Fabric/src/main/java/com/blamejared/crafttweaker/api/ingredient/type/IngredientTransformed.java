package com.blamejared.crafttweaker.api.ingredient.type;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.serializer.IngredientTransformedSerializer;
import com.blamejared.crafttweaker.api.ingredient.transform.IIngredientTransformer;
import com.faux.ingredientextension.api.ingredient.serializer.IIngredientSerializer;
import net.minecraft.world.item.crafting.Ingredient;

public class IngredientTransformed<I extends IIngredient, T extends IIngredientTransformed<I>> extends IngredientCraftTweaker<T> {
    
    public IngredientTransformed(T crtIngredient) {
        
        super(crtIngredient);
    }
    
    public IIngredientTransformer<I> getTransformer() {
        
        return getCrTIngredient().getTransformer();
    }
    
    @Override
    public IIngredientSerializer<? extends Ingredient> getSerializer() {
        
        return IngredientTransformedSerializer.INSTANCE;
    }
    
}
