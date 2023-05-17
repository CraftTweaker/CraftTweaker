package com.blamejared.crafttweaker.api.plugin;

import com.blamejared.crafttweaker.api.event.bus.EventBus;
import com.google.common.reflect.TypeToken;

public interface IEventRegistrationHandler {
    
    <T> void registerEventMapping(final TypeToken<T> event, final EventBus<T> bus);
    
}
