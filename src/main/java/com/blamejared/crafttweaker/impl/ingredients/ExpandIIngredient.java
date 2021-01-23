package com.blamejared.crafttweaker.impl.ingredients;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IIngredientWithAmount;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.item.transformed.IIngredientTransformer;
import com.blamejared.crafttweaker.impl.ingredients.transform.TransformCustom;
import com.blamejared.crafttweaker.impl.ingredients.transform.TransformDamage;
import com.blamejared.crafttweaker.impl.ingredients.transform.TransformReplace;
import com.blamejared.crafttweaker.impl.ingredients.transform.TransformReuse;
import com.blamejared.crafttweaker.impl.item.transformed.MCIngredientTransformed;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Function;

@ZenRegister
@ZenCodeType.Expansion("crafttweaker.api.item.IIngredient")
@Document("vanilla/api/items/ExpandIIngredientTransformers")
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
    
    @ZenCodeType.Method
    public static MCIngredientTransformed<IIngredient> reuse(IIngredient _this) {
        return new MCIngredientTransformed<>(_this, new TransformReuse<>());
    }
    
    /**
     * Use this if you already have the transformer from another ingredient
     */
    @ZenCodeType.Method
    public static MCIngredientTransformed<IIngredient> transform(IIngredient _this, IIngredientTransformer<IIngredient> transformer) {
        return new MCIngredientTransformed<>(_this, transformer);
    }
    
    /**
     * Use this in contexts where machines accept more than one item to state that fact.
     */
    @ZenCodeType.Method
    @ZenCodeType.Operator(ZenCodeType.OperatorType.MUL)
    public static IIngredientWithAmount mul(IIngredient _this, int amount) {
        return new IngredientWithAmount(_this, amount);
    }
    
    /**
     * Used implicitly when a machine can accept more than one item but you only provide one.
     */
    @ZenCodeType.Method
    @ZenCodeType.Caster(implicit = true)
    public static IIngredientWithAmount asIIngredientWithAmount(IIngredient _this) {
        if(_this instanceof IIngredientWithAmount){
            return (IIngredientWithAmount) _this;
        }
        return mul(_this, 1);
    }
}
