package com.blamejared.crafttweaker.api.event.bus;

import com.blamejared.crafttweaker.api.event.ICancelableEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

final class CancelingEventDispatcher<T extends ICancelableEvent> implements EventDispatcher<T> {
    
    private final Consumer<T> delegate;
    private final boolean listenToCanceled;
    
    CancelingEventDispatcher(final boolean listenToCanceled, final Consumer<T> delegate) {
        
        this.delegate = delegate;
        this.listenToCanceled = listenToCanceled;
    }
    
    @Override
    public void accept(final T t) {
        
        try {
            if(this.listenToCanceled || !t.canceled()) {
                this.delegate.accept(t);
            }
        } catch(final Throwable e) {
            throw new BusHandlingException(e);
        }
    }
    
    @NotNull
    @Override
    public Consumer<T> andThen(@NotNull final Consumer<? super T> after) {
        
        return new CancelingEventDispatcher<>(this.listenToCanceled, this.delegate.andThen(after));
    }
    
}
