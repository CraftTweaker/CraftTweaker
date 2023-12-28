package com.blamejared.crafttweaker.api.recipe.component;

import com.google.gson.reflect.TypeToken;
import net.minecraft.resources.ResourceLocation;

import java.util.Collection;
import java.util.List;
import java.util.function.BiPredicate;

final class SimpleRecipeComponent<T> implements IRecipeComponent<T> {
    
    private final ResourceLocation id;
    private final TypeToken<T> objectType;
    private final BiPredicate<T, T> matcher;
    
    SimpleRecipeComponent(final ResourceLocation id, final TypeToken<T> objectType, final BiPredicate<T, T> matcher) {
        
        this.id = id;
        this.objectType = objectType;
        this.matcher = matcher;
    }
    
    @Override
    public ResourceLocation id() {
        
        return this.id;
    }
    
    @Override
    public TypeToken<T> objectType() {
        
        return this.objectType;
    }
    
    @Override
    public boolean match(final T oracle, final T object) {
        
        return this.matcher.test(oracle, object);
    }
    
    @Override
    public Collection<T> unwrap(final T object) {
        
        return List.of(object);
    }
    
    @Override
    public T wrap(final Collection<T> objects) {
        
        if(objects.size() != 1) {
            throw new IllegalArgumentException("Unable to wrap more than 1 object for " + this.id() + " recipe component, but got " + objects);
        }
        
        return objects.iterator().next();
    }
    
    @Override
    public String toString() {
        
        return "SimpleComponent[" + this.id() + '/' + this.objectType() + ']';
    }
    
}
