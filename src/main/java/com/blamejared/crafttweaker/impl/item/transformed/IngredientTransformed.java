package com.blamejared.crafttweaker.impl.item.transformed;

import com.blamejared.crafttweaker.CraftTweakerRegistries;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IngredientVanillaPlus;
import com.blamejared.crafttweaker.api.item.transformed.IIngredientTransformer;
import com.google.gson.JsonElement;
import mcp.*;

@MethodsReturnNonnullByDefault
public class IngredientTransformed<I extends IIngredient, T extends MCIngredientTransformed<I>> extends IngredientVanillaPlus {
    private final T crtIngredient;

    public IngredientTransformed(T crtIngredient) {
        super(crtIngredient);
        this.crtIngredient = crtIngredient;
    }

    @Override
    public T getCrTIngredient() {
        return crtIngredient;
    }

    @Override
    public JsonElement serialize() {
        return getSerializer().toJson(this);
    }

    @Override
    public IngredientTransformedSerializer getSerializer() {
        return CraftTweakerRegistries.INGREDIENT_TRANSFORMED_SERIALIZER;
    }


    public IIngredientTransformer<I> getTransformer() {
        return crtIngredient.getTransformer();
    }
}
