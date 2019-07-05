package com.blamejared.crafttweaker.api.managers;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import net.minecraft.item.crafting.IRecipeType;
import org.openzen.zencode.java.ZenCodeType;

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
    
    
}
