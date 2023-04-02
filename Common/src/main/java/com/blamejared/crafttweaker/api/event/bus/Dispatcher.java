package com.blamejared.crafttweaker.api.event.bus;

import com.blamejared.crafttweaker.api.event.ICancelableEvent;
import com.blamejared.crafttweaker.api.event.IEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

final class Dispatcher<T extends IEvent<T>> implements Consumer<T> {
    
    private final Consumer<T> delegate;
    private final boolean listenToCanceled;
    private final boolean acceptCanceled;
    
    Dispatcher(final boolean listenToCanceled, final boolean acceptCanceled, final Consumer<T> delegate) {
        
        this.delegate = delegate;
        this.listenToCanceled = listenToCanceled;
        this.acceptCanceled = acceptCanceled;
    }
    
    @Override
    public void accept(final T t) {
        
        try {
            if(!this.acceptCanceled || (!((ICancelableEvent<?>) t).canceled() || this.listenToCanceled)) {
                this.delegate.accept(t);
            }
        } catch(final Throwable e) {
            throw new BusHandlingException(e);
        }
    }
    
    @NotNull
    @Override
    public Consumer<T> andThen(@NotNull final Consumer<? super T> after) {
        
        return new Dispatcher<>(this.listenToCanceled, this.acceptCanceled, this.delegate.andThen(after));
    }
    
}
