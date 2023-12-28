package com.blamejared.crafttweaker.impl.recipe.replacement;

import com.blamejared.crafttweaker.api.recipe.replacement.IReplacerRegistry;
import com.blamejared.crafttweaker.api.recipe.replacement.ITargetingFilter;
import com.blamejared.crafttweaker.api.recipe.replacement.ITargetingStrategy;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class ReplacerRegistry implements IReplacerRegistry {
    
    private final Collection<ITargetingFilter> castedFilters;
    private final Map<ResourceLocation, ITargetingStrategy> strategies;
    
    private final Collection<ITargetingFilter> filtersView;
    private final Collection<ResourceLocation> strategyNames;
    
    public ReplacerRegistry() {
        this.castedFilters = new ArrayList<>();
        this.strategies = new HashMap<>();
        this.filtersView = Collections.unmodifiableCollection(this.castedFilters);
        this.strategyNames = Collections.unmodifiableSet(this.strategies.keySet());
    }
    
    public void castedFilters(final Collection<ITargetingFilter> filters) {
        
        if (!this.castedFilters.isEmpty()) {
            
            throw new IllegalStateException("Attempted double registration for recipe filters");
        }
        
        this.castedFilters.addAll(filters);
    }
    
    public void strategy(final ResourceLocation id, final ITargetingStrategy strategy) {
        
        if (this.strategies.containsKey(id)) {
            
            throw new IllegalStateException("Attempted double strategy registration for " + id);
        }
        
        this.strategies.put(id, strategy);
    }
    
    @Override
    public Collection<ITargetingFilter> filters() {
        
        return this.filtersView;
    }
    
    @Override
    public ITargetingStrategy findStrategy(final ResourceLocation id) {
        
        return Objects.requireNonNull(this.strategies.get(id), () -> "Unknown strategy " + id);
    }
    
    @Override
    public Collection<ResourceLocation> allStrategyNames() {
        
        return this.strategyNames;
    }
    
}
