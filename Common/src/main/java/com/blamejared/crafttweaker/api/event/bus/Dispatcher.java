package com.blamejared.crafttweaker.api.event.bus;

import com.blamejared.crafttweaker.api.event.IEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

record Dispatcher<T extends IEvent<T>>(Consumer<T> delegate) implements Consumer<T> {
    
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
        
        return new Dispatcher<>(this.delegate.andThen(after));
    }
    
}
