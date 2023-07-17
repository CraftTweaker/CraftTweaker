package com.blamejared.crafttweaker.natives.recipe.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.mixin.common.access.recipe.AccessUpgradeRecipe;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.item.crafting.UpgradeRecipe;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/recipe/type/UpgradeRecipe")
@NativeTypeRegistration(value = UpgradeRecipe.class, zenCodeName = "crafttweaker.api.recipe.type.UpgradeRecipe")
public class ExpandUpgradeRecipe {
    
    @ZenCodeType.Getter("base")
    public static IIngredient getBase(UpgradeRecipe internal) {
        
        return IIngredient.fromIngredient(((AccessUpgradeRecipe) internal).crafttweaker$getBase());
    }
    
    @ZenCodeType.Getter("addition")
    public static IIngredient getAddition(UpgradeRecipe internal) {
        
        return IIngredient.fromIngredient(((AccessUpgradeRecipe) internal).crafttweaker$getAddition());
    }
    
}
