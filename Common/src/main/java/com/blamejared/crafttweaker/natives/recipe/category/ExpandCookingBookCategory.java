package com.blamejared.crafttweaker.natives.recipe.category;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.item.crafting.CookingBookCategory;

@ZenRegister
@Document("vanilla/api/recipe/CookingBookCategory")
@NativeTypeRegistration(value = CookingBookCategory.class, zenCodeName = "crafttweaker.api.recipe.CookingBookCategory")
@BracketEnum("minecraft:recipe/category/cooking")
public class ExpandCookingBookCategory {
}
