package com.blamejared.crafttweaker.impl_native.item;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.item.crafting.Ingredient;
import org.openzen.zencode.java.ZenCodeType;

/**
 * This is the vanilla ingredient type.
 * It is recommended that you use the {@link IIngredient} type whenever possible.
 *
 * This type can automatically be cast from and to {@link IIngredient}, though.
 */
@ZenRegister
@Document("vanilla/api/item/Ingredient")
@NativeTypeRegistration(value = Ingredient.class, zenCodeName = "crafttweaker.api.item.Ingredient")
public class ExpandNativeIngredient {
    
    @ZenCodeType.Method
    @ZenCodeType.Caster(implicit = true)
    public static IIngredient asIIngredient(Ingredient internal) {
        return IIngredient.fromIngredient(internal);
    }
}
