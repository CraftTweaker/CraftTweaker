package com.blamejared.crafttweaker.natives.recipe.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.item.crafting.BlastingRecipe;

@ZenRegister
@Document("vanilla/api/recipe/type/BlastingRecipe")
@NativeTypeRegistration(value = BlastingRecipe.class, zenCodeName = "crafttweaker.api.recipe.type.BlastingRecipe")
public class ExpandBlastingRecipe {
    
}
