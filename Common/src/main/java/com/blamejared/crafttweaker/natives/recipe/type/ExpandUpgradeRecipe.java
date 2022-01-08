package com.blamejared.crafttweaker.natives.recipe.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.item.crafting.UpgradeRecipe;

@ZenRegister
@Document("vanilla/api/recipe/type/type/UpgradeRecipe")
@NativeTypeRegistration(value = UpgradeRecipe.class, zenCodeName = "crafttweaker.api.recipe.type.UpgradeRecipe")
public class ExpandUpgradeRecipe {

}
