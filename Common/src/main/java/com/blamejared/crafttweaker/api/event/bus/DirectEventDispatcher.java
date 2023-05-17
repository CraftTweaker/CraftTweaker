package com.blamejared.crafttweaker.api.event.bus;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

final class DirectEventDispatcher<T> implements EventDispatcher<T> {
    
    private final Consumer<T> delegate;
    
    DirectEventDispatcher(final Consumer<T> delegate) {
        
        this.delegate = delegate;
    }
    
    @Override
    public void accept(final T t) {
        
        try {
            this.delegate.accept(t);
        } catch(final Throwable e) {
            throw new BusHandlingException(e);
        }
    }
    
    @NotNull
    @Override
    public Consumer<T> andThen(@NotNull final Consumer<? super T> after) {
        
        return new DirectEventDispatcher<>(this.delegate.andThen(after));
    }
    
}
