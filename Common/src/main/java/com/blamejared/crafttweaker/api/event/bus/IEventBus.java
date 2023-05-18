package com.blamejared.crafttweaker.api.event.bus;

import com.blamejared.crafttweaker.api.event.IEventCancellationCarrier;
import com.blamejared.crafttweaker.api.event.Phase;
import com.google.common.reflect.TypeToken;

import java.util.Objects;
import java.util.function.Consumer;

public interface IEventBus<T> {
    
    static <T> IEventBus<T> direct(final Class<T> clazz, final IEventBusWire wire) {
        
        Objects.requireNonNull(clazz, "class");
        Objects.requireNonNull(wire, "wire");
        return direct(TypeToken.of(clazz), wire);
    }
    
    static <T> IEventBus<T> direct(final TypeToken<T> token, final IEventBusWire wire) {
        
        Objects.requireNonNull(token, "token");
        Objects.requireNonNull(wire, "wire");
        return DirectEventBus.of(token, wire);
    }
    
    static <T> IEventBus<T> cancelable(final Class<T> clazz, final IEventBusWire wire, final IEventCancellationCarrier<T> carrier) {
        
        Objects.requireNonNull(clazz, "class");
        Objects.requireNonNull(wire, "wire");
        Objects.requireNonNull(carrier, "carrier");
        return cancelable(TypeToken.of(clazz), wire, carrier);
    }
    
    static <T> IEventBus<T> cancelable(final TypeToken<T> token, final IEventBusWire wire, final IEventCancellationCarrier<T> carrier) {
        
        Objects.requireNonNull(token, "token");
        Objects.requireNonNull(wire, "wire");
        Objects.requireNonNull(carrier, "carrier");
        return CancelingEventBus.of(token, wire, carrier);
    }
    
    IHandlerToken<T> registerHandler(final Consumer<T> handler);
    IHandlerToken<T> registerHandler(final Phase phase, final Consumer<T> handler);
    IHandlerToken<T> registerHandler(final boolean listenToCanceled, final Consumer<T> handler);
    IHandlerToken<T> registerHandler(final Phase phase, final boolean listenToCanceled, final Consumer<T> handler);
    
    void unregisterHandler(final IHandlerToken<T> token);
    
    T post(final Phase phase, final T event);
    T postCatching(final Phase phase, final T event, final Consumer<BusHandlingException> exceptionHandler);
    
    TypeToken<T> eventType();
    
}
