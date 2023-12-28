package com.blamejared.crafttweaker.natives.recipe.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.item.crafting.SmeltingRecipe;

@ZenRegister
@Document("vanilla/api/recipe/type/SmeltingRecipe")
@NativeTypeRegistration(value = SmeltingRecipe.class, zenCodeName = "crafttweaker.api.recipe.type.SmeltingRecipe")
public class ExpandSmeltingRecipe {
    
}
