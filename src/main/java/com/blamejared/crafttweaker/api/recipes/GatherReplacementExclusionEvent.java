package com.blamejared.crafttweaker.api.recipes;

import com.blamejared.crafttweaker.api.managers.IRecipeManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.Event;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class GatherReplacementExclusionEvent extends Event {
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
    
    public void addExclusion(final IRecipe<?> id) {
        this.excludedRecipes.add(id.getId());
    }
}
