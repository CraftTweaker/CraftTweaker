package com.blamejared.crafttweaker.api.recipe.component;

import com.blamejared.crafttweaker.api.util.GenericUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Creates a simple instance of a {@link IDecomposedRecipe} with the given components.
 *
 * <p>An instance of this class can be obtained through {@link IDecomposedRecipe#builder()}. It is highly suggested to
 * use this builder rather than implement the interface yourself.</p>
 *
 * @since 10.0.0
 */
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
    
    /**
     * Adds the given {@link IRecipeComponent} to the recipe, pointing to the given {@link List}.
     *
     * <p>The contract of this method is the same as {@link IDecomposedRecipe#set(IRecipeComponent, List)}, except it
     * operates on a recipe builder.</p>
     *
     * @param component The component that needs to be added to the recipe.
     * @param object    The {@link List} containing the data pointed to by the component.
     * @param <C>       The type of objects pointed to by the component.
     *
     * @return This builder for chaining.
     *
     * @since 10.0.0
     */
    public <C> DecomposedRecipeBuilder with(final IRecipeComponent<C> component, final List<C> object) {
        
        this.components.put(component, object);
        return this;
    }
    
    /**
     * Builds a {@link IDecomposedRecipe} with the given set of components.
     *
     * @return A new {@link IDecomposedRecipe} with the specified set of components.
     *
     * @since 10.0.0
     */
    public IDecomposedRecipe build() {
        
        return new SimpleDecomposedRecipe(new HashMap<>(this.components));
    }
    
}
