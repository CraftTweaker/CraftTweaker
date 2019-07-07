package com.blamejared.crafttweaker.api.managers;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionRemoveRecipeByModid;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionRemoveRecipeByName;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionRemoveRecipeByOutput;
import com.blamejared.crafttweaker.impl.actions.recipes.ActionRemoveRecipeByRegex;
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
     * Remove a recipe based on it's output.
     *
     * @param output output of the recipe
     */
    @ZenCodeType.Method
    default void removeRecipe(IItemStack output) {
        CraftTweakerAPI.apply(new ActionRemoveRecipeByOutput(this, output));
    }
    
    /**
     * Remove recipe based on Registry name
     *
     * @param name registry name of recipe to remove
     */
    @ZenCodeType.Method
    default void removeByName(String name) {
        CraftTweakerAPI.apply(new ActionRemoveRecipeByName(this, new ResourceLocation(name)));
    }
    
    /**
     * Remove recipe based on Registry name modid
     *
     * @param modid modid of the recipes to remove
     */
    @ZenCodeType.Method
    default void removeByModid(String modid) {
        CraftTweakerAPI.apply(new ActionRemoveRecipeByModid(this, modid));
    }
    
    /**
     * Remove recipe based on regex
     *
     * @param regex regex to match against
     */
    @ZenCodeType.Method
    default void removeByRegex(String regex) {
        CraftTweakerAPI.apply(new ActionRemoveRecipeByRegex(this, regex));
    }
    
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
    
    
    @FunctionalInterface
    @ZenRegister
    interface RecipeFunctionSingle {
        
        IItemStack process(IItemStack usualOut, IItemStack inputs);
    }
    
    @FunctionalInterface
    @ZenRegister
    interface RecipeFunctionArray {
        
        IItemStack process(IItemStack usualOut, IItemStack[] inputs);
    }
    
    @FunctionalInterface
    @ZenRegister
    interface RecipeFunctionMatrix {
        
        IItemStack process(IItemStack usualOut, IItemStack[][] inputs);
    }
    
}
