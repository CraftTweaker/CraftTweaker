package com.blamejared.crafttweaker.natives.recipe.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.item.crafting.SmithingRecipe;

@ZenRegister
@Document("vanilla/api/recipe/type/SmithingRecipe")
@NativeTypeRegistration(value = SmithingRecipe.class, zenCodeName = "crafttweaker.api.recipe.type.SmithingRecipe")
public class ExpandUpgradeRecipe {

}
