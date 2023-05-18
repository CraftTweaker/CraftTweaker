package com.blamejared.crafttweaker.api.event;

import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.google.common.reflect.TypeToken;

public interface IEventRegistry {
    
    <T> IEventBus<T> busOf(final TypeToken<T> eventType);
    
    default <T> IEventBus<T> busOf(final Class<T> clazz) {
        return this.busOf(TypeToken.of(clazz));
    }
    
}
