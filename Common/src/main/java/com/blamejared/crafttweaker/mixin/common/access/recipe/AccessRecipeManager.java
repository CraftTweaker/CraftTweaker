package com.blamejared.crafttweaker.mixin.common.access.recipe;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Map;

@Mixin(RecipeManager.class)
public interface AccessRecipeManager {
    
    @Invoker("fromJson")
    static RecipeHolder<?> crafttweaker$callFromJson(ResourceLocation id, JsonObject json) {throw new UnsupportedOperationException();}
    
    
    @Accessor("recipes")
    Map<RecipeType<?>, Map<ResourceLocation, RecipeHolder<?>>> crafttweaker$getRecipes();
    
    @Accessor("recipes")
    void crafttweaker$setRecipes(Map<RecipeType<?>, Map<ResourceLocation, RecipeHolder<?>>> recipeMap);
    
    @Accessor("byName")
    Map<ResourceLocation, RecipeHolder<?>> crafttweaker$getByName();
    
    @Accessor("byName")
    void crafttweaker$setByName(Map<ResourceLocation, RecipeHolder<?>> byName);
    
}
