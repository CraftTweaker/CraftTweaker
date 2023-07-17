package com.blamejared.crafttweaker.natives.recipe.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.mixin.common.access.recipe.AccessLegacyUpgradeRecipe;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.item.crafting.LegacyUpgradeRecipe;
import org.openzen.zencode.java.ZenCodeType;

@SuppressWarnings("removal")
@ZenRegister
@Document("vanilla/api/recipe/type/LegacyUpgradeRecipe")
@NativeTypeRegistration(value = LegacyUpgradeRecipe.class, zenCodeName = "crafttweaker.api.recipe.type.LegacyUpgradeRecipe")
public class ExpandLegacyUpgradeRecipe {
    
    @ZenCodeType.Getter("base")
    public static IIngredient getBase(LegacyUpgradeRecipe internal) {
        
        return IIngredient.fromIngredient(((AccessLegacyUpgradeRecipe) internal).crafttweaker$getBase());
    }
    
    @ZenCodeType.Getter("addition")
    public static IIngredient getAddition(LegacyUpgradeRecipe internal) {
        
        return IIngredient.fromIngredient(((AccessLegacyUpgradeRecipe) internal).crafttweaker$getAddition());
    }
    
}
