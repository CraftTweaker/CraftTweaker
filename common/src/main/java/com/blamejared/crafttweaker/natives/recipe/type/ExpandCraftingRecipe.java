package com.blamejared.crafttweaker.natives.recipe.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.item.crafting.CraftingRecipe;

@ZenRegister
@Document("vanilla/api/recipe/type/CraftingRecipe")
@NativeTypeRegistration(value = CraftingRecipe.class, zenCodeName = "crafttweaker.api.recipe.type.CraftingRecipe")
public class ExpandCraftingRecipe {
    
}
