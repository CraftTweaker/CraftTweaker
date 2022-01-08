package com.blamejared.crafttweaker.natives.item;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.item.crafting.Ingredient;
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
public class ExpandIngredient {
    
    @ZenCodeType.Method
    @ZenCodeType.Caster(implicit = true)
    public static IIngredient asIIngredient(Ingredient internal) {
        
        return IIngredient.fromIngredient(internal);
    }
    
}
