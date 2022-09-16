package com.blamejared.crafttweaker.api.recipe.component;

import com.blamejared.crafttweaker.api.util.GenericUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class DecomposedRecipeBuilder {
    
    private static final class SimpleDecomposedRecipe implements IDecomposedRecipe {
        
        private final Map<IRecipeComponent<?>, List<?>> components;
        
        SimpleDecomposedRecipe(final Map<IRecipeComponent<?>, List<?>> components) {
            
            this.components = new HashMap<>();
            components.forEach((key, value) -> this.components.put(key, List.copyOf(value)));
        }
        
        @Override
        public <C> List<C> get(final IRecipeComponent<C> component) {
            
            return GenericUtil.uncheck(this.components.get(this.verify(component)));
        }
        
        @Override
        public <C> void set(final IRecipeComponent<C> component, final List<C> object) {
            
            this.components.put(this.verify(component), List.copyOf(object));
        }
        
        @Override
        public Set<IRecipeComponent<?>> components() {
            
            return this.components.keySet();
        }
        
        private <C> IRecipeComponent<C> verify(final IRecipeComponent<C> component) {
            
            try {
                return IRecipeComponent.find(component.id());
            } catch(final IllegalArgumentException e) {
                throw new IllegalStateException("Component " + component + " was not registered", e);
            }
        }
        
    }
    
    private final Map<IRecipeComponent<?>, List<?>> components;
    
    private DecomposedRecipeBuilder() {
        
        this.components = new HashMap<>();
    }
    
    static DecomposedRecipeBuilder of() {
        
        return new DecomposedRecipeBuilder();
    }
    
    public <C> DecomposedRecipeBuilder with(final IRecipeComponent<C> component, final List<C> object) {
        
        this.components.put(component, object);
        return this;
    }
    
    public IDecomposedRecipe build() {
        
        return new SimpleDecomposedRecipe(new HashMap<>(this.components));
    }
    
}
