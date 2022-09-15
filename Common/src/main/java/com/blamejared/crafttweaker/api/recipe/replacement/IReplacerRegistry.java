package com.blamejared.crafttweaker.api.recipe.replacement;

import net.minecraft.resources.ResourceLocation;

import java.util.Collection;

public interface IReplacerRegistry {
    
    Collection<ITargetingFilter> filters();
    
    ITargetingStrategy findStrategy(final ResourceLocation id);
    
    Collection<ResourceLocation> allStrategyNames();
    
}
