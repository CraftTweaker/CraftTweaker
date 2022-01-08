package com.blamejared.crafttweaker.natives.recipe.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.item.crafting.SmokingRecipe;

@ZenRegister
@Document("vanilla/api/recipe/type/SmokingRecipe")
@NativeTypeRegistration(value = SmokingRecipe.class, zenCodeName = "crafttweaker.api.recipe.type.SmokingRecipe")
public class ExpandSmokingRecipe {
    
}
