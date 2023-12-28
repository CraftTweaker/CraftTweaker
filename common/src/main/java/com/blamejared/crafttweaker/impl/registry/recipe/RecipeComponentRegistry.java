package com.blamejared.crafttweaker.impl.registry.recipe;

import com.blamejared.crafttweaker.api.recipe.component.IRecipeComponent;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import net.minecraft.resources.ResourceLocation;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class RecipeComponentRegistry {
    
    private final Map<ResourceLocation, IRecipeComponent<?>> components = new HashMap<>();
    
    public void registerComponents(final Collection<IRecipeComponent<?>> components) {
        
        if(!this.components.isEmpty()) {
            throw new IllegalStateException("Components have already been registered");
        }
        components.forEach(it -> this.components.put(it.id(), it));
    }
    
    public <T> IRecipeComponent<T> find(final ResourceLocation id) {
        
        return GenericUtil.uncheck(this.components.computeIfAbsent(id, it -> {
            throw new IllegalArgumentException("No component with id '" + id + "' registered; are you too early?");
        }));
    }
    
    public Collection<IRecipeComponent<?>> allComponents() {
        
        return Collections.unmodifiableCollection(this.components.values());
    }
    
}
