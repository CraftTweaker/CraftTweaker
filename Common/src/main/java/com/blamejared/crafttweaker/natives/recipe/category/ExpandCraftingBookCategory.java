package com.blamejared.crafttweaker.natives.recipe.category;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.CraftingBookCategory;

@ZenRegister
@Document("vanilla/api/recipe/CraftingBookCategory")
@NativeTypeRegistration(value = CraftingBookCategory.class, zenCodeName = "crafttweaker.api.recipe.CraftingBookCategory")
@BracketEnum("minecraft:recipe/category/crafting")
public class ExpandCraftingBookCategory {
}
