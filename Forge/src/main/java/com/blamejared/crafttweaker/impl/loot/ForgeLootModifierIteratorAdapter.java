package com.blamejared.crafttweaker.impl.loot;


import java.util.Iterator;
import java.util.function.Function;

@SuppressWarnings("ClassCanBeRecord")
final class ForgeLootModifierIteratorAdapter<T, U> implements Iterator<T> {
    
    private final Iterator<U> wrapped;
    private final Function<U, T> converter;
    
    private ForgeLootModifierIteratorAdapter(final Iterator<U> wrapped, final Function<U, T> converter) {
        
        this.wrapped = wrapped;
        this.converter = converter;
    }
    
    static <T, U> Iterator<T> adapt(final Iterator<U> iterator, final Function<U, T> converter) {
        
        return new ForgeLootModifierIteratorAdapter<>(iterator, converter);
    }
    
    @Override
    public void remove() {
        
        this.wrapped.remove();
    }
    
    @Override
    public boolean hasNext() {
        
        return this.wrapped.hasNext();
    }
    
    @Override
    public T next() {
        
        return this.converter.apply(this.wrapped.next());
    }
    
}
