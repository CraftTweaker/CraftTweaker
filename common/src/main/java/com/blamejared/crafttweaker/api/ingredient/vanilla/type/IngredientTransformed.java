package com.blamejared.crafttweaker.api.ingredient.vanilla.type;

import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.transform.IIngredientTransformer;
import com.blamejared.crafttweaker.api.ingredient.type.IIngredientTransformed;
import com.blamejared.crafttweaker.api.ingredient.vanilla.serializer.IngredientTransformedSerializer;
import com.blamejared.crafttweaker.platform.Services;
import net.minecraft.world.item.crafting.Ingredient;

public class IngredientTransformed<I extends IIngredient, T extends IIngredientTransformed<I>> extends IngredientCraftTweaker<T> {
    
    public static <I extends IIngredient, T extends IIngredientTransformed<I>> IngredientTransformed<I, T> of(T crtIngredient) {
        
        return new IngredientTransformed<>(crtIngredient);
    }
    
    public static <I extends IIngredient, T extends IIngredientTransformed<I>> Ingredient ingredient(T crtIngredient) {
        
        return Services.PLATFORM.getIngredientTransformed(crtIngredient);
    }
    
    private IngredientTransformed(T crtIngredient) {
        
        super(crtIngredient);
    }
    
    @Override
    public T getCrTIngredient() {
        
        return super.getCrTIngredient();
    }
    
    public IIngredientTransformer<I> getTransformer() {
        
        return getCrTIngredient().getTransformer();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public IngredientTransformedSerializer serializer() {
        
        return IngredientTransformedSerializer.INSTANCE;
    }
    
}
