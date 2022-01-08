package com.blamejared.crafttweaker.mixin.common.access.recipe;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(RecipeManager.class)
public interface AccessRecipeManager {
    
    @Accessor("recipes")
    Map<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> getRecipes();
    
    @Accessor("recipes")
    void setRecipes(Map<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> recipeMap);
    
}
