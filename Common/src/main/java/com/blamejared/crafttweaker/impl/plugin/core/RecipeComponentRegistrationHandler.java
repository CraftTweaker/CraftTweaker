package com.blamejared.crafttweaker.impl.plugin.core;

import com.blamejared.crafttweaker.api.plugin.IRecipeComponentRegistrationHandler;
import com.blamejared.crafttweaker.api.recipe.component.IRecipeComponent;
import net.minecraft.resources.ResourceLocation;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

final class RecipeComponentRegistrationHandler implements IRecipeComponentRegistrationHandler {
    
    private final Map<ResourceLocation, IRecipeComponent<?>> components;
    
    private RecipeComponentRegistrationHandler() {
        
        this.components = new HashMap<>();
    }
    
    static Collection<IRecipeComponent<?>> gather(final Consumer<IRecipeComponentRegistrationHandler> populatingConsumer) {
        
        final RecipeComponentRegistrationHandler handler = new RecipeComponentRegistrationHandler();
        populatingConsumer.accept(handler);
        return Collections.unmodifiableCollection(handler.components.values());
    }
    
    @Override
    public <T> void registerRecipeComponent(final IRecipeComponent<T> component) {
        
        final ResourceLocation id = component.id();
        if(this.components.containsKey(id)) {
            
            throw new IllegalArgumentException(
                    "Component '" + id + "' was already registered with type " + this.components.get(id).objectType()
            );
        }
        
        this.components.put(id, component);
    }
    
}
