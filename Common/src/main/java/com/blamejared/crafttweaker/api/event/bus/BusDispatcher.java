package com.blamejared.crafttweaker.api.event.bus;

import com.blamejared.crafttweaker.api.event.Phase;

import java.util.function.Consumer;

sealed interface BusDispatcher<T> permits ArrayBackedDispatcher, ListBackedDispatcher, SelectiveBackingDispatcher {
    
    HandlerToken<T> register(final boolean listenToCanceled, final Phase phase, final Consumer<T> consumer);
    
    void unregister(final HandlerToken<T> token);
    
    T dispatch(final T event);
    
}
