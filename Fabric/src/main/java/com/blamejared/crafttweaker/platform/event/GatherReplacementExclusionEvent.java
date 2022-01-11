package com.blamejared.crafttweaker.platform.event;


import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.recipe.replacement.event.IGatherReplacementExclusionEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class GatherReplacementExclusionEvent implements IGatherReplacementExclusionEvent {
    
    private final IRecipeManager targetedManager;
    private final Set<ResourceLocation> excludedRecipes;
    
    public GatherReplacementExclusionEvent(final IRecipeManager manager) {
        
        this.targetedManager = manager;
        this.excludedRecipes = new HashSet<>();
    }
    
    public IRecipeManager getTargetedManager() {
        
        return this.targetedManager;
    }
    
    public Collection<ResourceLocation> getExcludedRecipes() {
        
        return Collections.unmodifiableCollection(this.excludedRecipes);
    }
    
    public void addExclusion(final ResourceLocation id) {
        
        this.excludedRecipes.add(id);
    }
    
    public void addExclusion(final Recipe<?> recipe) {
        
        this.addExclusion(recipe.getId());
    }
    
}
