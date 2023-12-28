package com.blamejared.crafttweaker.platform.helper;

import com.blamejared.crafttweaker.mixin.common.access.recipe.AccessRecipeManager;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.function.Function;

// TODO("Better package")
public interface IAccessibleElementsProvider {
    
    RecipeManager recipeManager();
    
    AccessRecipeManager accessibleRecipeManager();
    
    void recipeManager(final RecipeManager manager);
    
    RegistryAccess registryAccess();
    
    default <T> T registryAccess(Function<RegistryAccess, T> func){
        return func.apply(registryAccess());
    }
    
    boolean hasRegistryAccess();
    
    IAccessibleClientElementsProvider client();
    
    IAccessibleServerElementsProvider server();
    
    
}
