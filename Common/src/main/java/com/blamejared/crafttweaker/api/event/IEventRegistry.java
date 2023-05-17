package com.blamejared.crafttweaker.api.event;

import com.blamejared.crafttweaker.api.event.bus.EventBus;
import com.google.common.reflect.TypeToken;

public interface IEventRegistry {
    
    <T> EventBus<T> busOf(final TypeToken<T> eventType);
    
    default <T> EventBus<T> busOf(final Class<T> clazz) {
        return this.busOf(TypeToken.of(clazz));
    }
    
}
