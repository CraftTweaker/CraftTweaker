package com.blamejared.crafttweaker.natives.recipe.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.item.crafting.StonecutterRecipe;

@ZenRegister
@Document("vanilla/api/recipe/type/StonecutterRecipe")
@NativeTypeRegistration(value = StonecutterRecipe.class, zenCodeName = "crafttweaker.api.recipe.type.StonecutterRecipe")
public class ExpandStonecutterRecipe {

}
