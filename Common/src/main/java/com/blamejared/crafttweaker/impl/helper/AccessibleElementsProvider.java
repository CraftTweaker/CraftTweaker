package com.blamejared.crafttweaker.impl.helper;

import com.blamejared.crafttweaker.mixin.common.access.recipe.AccessRecipeManager;
import com.blamejared.crafttweaker.platform.helper.IAccessibleClientElementsProvider;
import com.blamejared.crafttweaker.platform.helper.IAccessibleElementsProvider;
import com.blamejared.crafttweaker.platform.helper.IAccessibleServerElementsProvider;
import com.google.common.base.Suppliers;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.Objects;
import java.util.function.Supplier;

public final class AccessibleElementsProvider implements IAccessibleElementsProvider {
    
    private static final Supplier<AccessibleElementsProvider> INSTANCE = Suppliers.memoize(AccessibleElementsProvider::new);
    
    private RecipeManager recipeManager;
    
    private AccessibleElementsProvider() {
        
        this.recipeManager = null;
    }
    
    public static IAccessibleElementsProvider get() {
        
        return INSTANCE.get();
    }
    
    @Override
    public RecipeManager recipeManager() {
        
        return Objects.requireNonNull(this.recipeManager, "Recipe manager is unavailable");
    }
    
    @Override
    public AccessRecipeManager accessibleRecipeManager() {
        
        return (AccessRecipeManager) this.recipeManager();
    }
    
    @Override
    public void recipeManager(final RecipeManager manager) {
        
        this.recipeManager = manager;
    }
    
    @Override
    public RegistryAccess registryAccess() {
        
        if(this.server().hasRegistryAccess()) {
            return this.server().registryAccess();
        } else if(this.client().hasRegistryAccess()) {
            return this.client().registryAccess();
        }
        throw new NullPointerException("Registry access is unavailable");
    }
    
    @Override
    public boolean hasRegistryAccess() {
        
        return client().hasRegistryAccess() || server().hasRegistryAccess();
    }
    
    @Override
    public IAccessibleClientElementsProvider client() {
        
        return AccessibleClientElementsProvider.get();
    }
    
    @Override
    public IAccessibleServerElementsProvider server() {
        
        return AccessibleServerElementsProvider.get();
    }
    
}
