package com.blamejared.crafttweaker.api.event.bus;

import com.blamejared.crafttweaker.api.event.ICancelableEvent;
import com.blamejared.crafttweaker.api.event.IEvent;
import com.blamejared.crafttweaker.api.event.Phase;

import java.util.function.Consumer;

sealed interface BusDispatcher<T extends IEvent<T>> permits ArrayBackedDispatcher, ListBackedDispatcher, SelectiveBackingDispatcher {
    
    @SuppressWarnings("unchecked")
    static <T extends IEvent<T>, U extends ICancelableEvent<U> & IEvent<U>> Consumer<U> wrap(final boolean listenToCanceled, final Consumer<T> consumer) {
        
        return it -> {
            if(!it.canceled() || listenToCanceled) {
                consumer.accept((T) consumer);
            }
        };
    }
    
    HandlerToken<T> register(final boolean listenToCanceled, final Phase phase, final Consumer<T> consumer);
    
    void unregister(final HandlerToken<T> token);
    
    T dispatch(final T event);
    
}
