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
    Map<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> crafttweaker$getRecipes();
    
    @Accessor("recipes")
    void crafttweaker$setRecipes(Map<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> recipeMap);
    
    @Accessor("byName")
    Map<ResourceLocation, Recipe<?>> crafttweaker$getByName();
    
    @Accessor("byName")
    void crafttweaker$setByName(Map<ResourceLocation, Recipe<?>> byName);
    
}
