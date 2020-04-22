package com.blamejared.crafttweaker.impl.ingredients;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.item.transformed.IIngredientTransformer;
import com.blamejared.crafttweaker.impl.ingredients.transform.TransformCustom;
import com.blamejared.crafttweaker.impl.ingredients.transform.TransformDamage;
import com.blamejared.crafttweaker.impl.ingredients.transform.TransformReplace;
import com.blamejared.crafttweaker.impl.item.transformed.IngredientTransformed;
import com.blamejared.crafttweaker.impl.item.transformed.MCIngredientTransformed;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Function;

@ZenCodeType.Expansion("crafttweaker.api.item.IIngredient")
@ZenRegister
public class ExpandIIngredient {

    @ZenCodeType.Method
    public static MCIngredientTransformed<IIngredient> transformReplace(IIngredient _this, IItemStack replaceWith) {
        return new MCIngredientTransformed<>(_this, new TransformReplace<>(replaceWith));
    }

    @ZenCodeType.Method
    public static MCIngredientTransformed<IIngredient> transformDamage(IIngredient _this, @ZenCodeType.OptionalInt(1) int amount) {
        return new MCIngredientTransformed<>(_this, new TransformDamage<>(amount));
    }

    @ZenCodeType.Method
    public static MCIngredientTransformed<IIngredient> transformCustom(IIngredient _this, String uid, @ZenCodeType.Optional Function<IItemStack, IItemStack> function) {
        return new MCIngredientTransformed<>(_this, new TransformCustom<>(uid, function));
    }

    /**
     * Use this if you already have the transformer from another ingredient
     */
    @ZenCodeType.Method
    public static MCIngredientTransformed<IIngredient> transform(IIngredient _this, IIngredientTransformer<IIngredient> transformer) {
        return new MCIngredientTransformed<>(_this, transformer);
    }
}
