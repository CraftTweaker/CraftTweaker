package com.blamejared.crafttweaker.api.recipe.component;

import com.google.gson.reflect.TypeToken;
import net.minecraft.resources.ResourceLocation;

import java.util.Collection;
import java.util.function.BiPredicate;
import java.util.function.Function;

final class ListRecipeComponent<T> implements IRecipeComponent<T> {
    
    private final ResourceLocation id;
    private final TypeToken<T> objectType;
    private final BiPredicate<T, T> matcher;
    private final Function<T, Collection<T>> unwrappingFunction;
    private final Function<Collection<T>, T> wrapper;
    
    ListRecipeComponent(
            final ResourceLocation id,
            final TypeToken<T> objectType,
            final BiPredicate<T, T> matcher,
            final Function<T, Collection<T>> unwrappingFunction,
            final Function<Collection<T>, T> wrapper
    ) {
        
        this.id = id;
        this.objectType = objectType;
        this.matcher = matcher;
        this.unwrappingFunction = unwrappingFunction;
        this.wrapper = wrapper;
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
        
        return this.unwrappingFunction.apply(object);
    }
    
    @Override
    public T wrap(final Collection<T> objects) {
        
        return this.wrapper.apply(objects);
    }
    
}
