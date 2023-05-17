package com.blamejared.crafttweaker.impl.plugin.core;

import com.blamejared.crafttweaker.api.event.bus.EventBus;
import com.blamejared.crafttweaker.api.plugin.IEventRegistrationHandler;
import com.google.common.reflect.TypeToken;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

final class EventRegistrationHandler implements IEventRegistrationHandler {
    
    private final Map<TypeToken<?>, EventBus<?>> eventBuses;
    
    private EventRegistrationHandler() {
        
        this.eventBuses = new HashMap<>();
    }
    
    static Map<TypeToken<?>, EventBus<?>> gather(final Consumer<IEventRegistrationHandler> populatingConsumer) {
        
        final EventRegistrationHandler handler = new EventRegistrationHandler();
        populatingConsumer.accept(handler);
        return Collections.unmodifiableMap(handler.eventBuses);
    }
    
    @Override
    public <T> void registerEventMapping(final TypeToken<T> event, final EventBus<T> bus) {
        
        if (this.eventBuses.containsKey(event)) {
            throw new IllegalArgumentException("Event " + event + " was already registered with a different bus");
        }
        
        this.eventBuses.put(event, bus);
    }
    
}
