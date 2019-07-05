package com.blamejared.crafttweaker.api.managers;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.managers.CTRecipeManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Map;

/**
 * Default interface for Registry based handlers as they can all remove recipes by ResourceLocation.
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.registries.IRecipeManager")
public interface IRecipeManager {
    
    /**
     * Remove recipe based on Registry name
     *
     * @param name registry name of recipe to remove
     */
    @ZenCodeType.Method
    void removeByName(String name);
    
    
    /**
     * Remove a recipe based on it's output.
     *
     * @param output output of the recipe
     */
    @ZenCodeType.Method
    void remove(IItemStack output);
    
    /**
     * Gets the recipe type for the registry to remove from.
     *
     * @return IRecipeType of this registry.
     */
    IRecipeType getRecipeType();
    
    /**
     * Gets all the vanilla IRecipes for this recipe type.
     *
     * @return Map of ResourceLocation to IRecipe for this recipe type.
     */
    default Map<ResourceLocation, IRecipe<?>> getRecipes() {
        return CTRecipeManager.recipeManager.recipes.get(getRecipeType());
    }
}
