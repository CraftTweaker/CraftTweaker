package com.blamejared.crafttweaker.platform.helper;

import com.blamejared.crafttweaker.mixin.common.access.recipe.AccessRecipeManager;
import net.minecraft.world.item.crafting.RecipeManager;

// TODO("Better package")
public interface IAccessibleElementsProvider {
    
    RecipeManager recipeManager();
    
    AccessRecipeManager accessibleRecipeManager();
    
    void recipeManager(final RecipeManager manager);
    
    IAccessibleClientElementsProvider client();
    
    IAccessibleServerElementsProvider server();
    
    
}
