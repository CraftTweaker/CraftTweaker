package com.blamejared.crafttweaker.api.plugin;

import com.blamejared.crafttweaker.api.recipe.replacement.ITargetingFilter;
import com.blamejared.crafttweaker.api.recipe.replacement.ITargetingStrategy;
import net.minecraft.resources.ResourceLocation;

public interface IReplacerComponentRegistrationHandler {
    
    void registerTargetingFilter(final ITargetingFilter filter);
    
    void registerTargetingStrategy(final ResourceLocation id, final ITargetingStrategy strategy);
    
}
