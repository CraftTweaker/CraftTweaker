package com.blamejared.crafttweaker.impl.registry;

import com.blamejared.crafttweaker.api.event.IEventRegistry;
import com.blamejared.crafttweaker.api.event.bus.EventBus;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.google.common.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

final class EventRegistry implements IEventRegistry {
    
    private final Map<TypeToken<?>, EventBus<?>> buses;
    
    EventRegistry() {
        this.buses = new HashMap<>();
    }
    
    <T> void registerBusMapping(final TypeToken<T> token, final EventBus<T> bus) {
        
        if (this.buses.containsKey(token)) {
            throw new IllegalStateException("Duplicate registration attempted for " + token);
        }
        
        this.buses.put(token, bus);
    }
    
    @Override
    public <T> EventBus<T> busOf(final TypeToken<T> eventType) {
        
        return GenericUtil.uncheck(this.buses.computeIfAbsent(eventType, it -> {
            throw new IllegalArgumentException("Unknown event type " + it);
        }));
    }
    
}
