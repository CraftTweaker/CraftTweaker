package com.blamejared.crafttweaker.api.plugin;

import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.google.common.reflect.TypeToken;

public interface IEventRegistrationHandler {
    
    <T> void registerEventMapping(final TypeToken<T> event, final IEventBus<T> bus);
    
}
